package app.core;

import app.annotations.Column;
import app.annotations.Id;
import app.contracts.DbContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;
    private Class<E> klass;

    public EntityManager(Connection connection, Class<E> klass) throws SQLException {
        this.connection = connection;
        this.klass = klass;
        if (!checkIfDatabaseExists()) {
            createTable();
        } else if (checkIfClassHasNewFields(klass)) {
            updateTableFields();
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getId((Class<E>) entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);
        if (value == null || (int) value <= 0) {
            return this.doInsert(entity);
        } else {
            return this.doUpdate(entity);
        }
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, InstantiationException {
        String query = "SELECT * FROM " + getTableName() + "";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        List<E> entities = new ArrayList<>();
        Constructor<E> constructor = (Constructor<E>) table.getDeclaredConstructor();
        E entity = constructor.newInstance();
        fillListWithEntity(table, entity, rs, entities);
        return entities;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException,
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + where + "";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        List<E> entities = new ArrayList<>();
        Constructor<E> constructor = (Constructor<E>) table.getDeclaredConstructor();
        E entity = constructor.newInstance();
        fillListWithEntity(table, entity, rs, entities);
        return entities;

    }


    private void fillListWithEntity(Class<E> table, E entity, ResultSet rs, List<E> entities)
            throws SQLException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();

        while (rs.next()) {
            for (Field field : fields) {
                field.setAccessible(true);
                String columnName = field.getAnnotation(Column.class).name();
                if (field.getType() == int.class) {
                    int value = rs.getInt(columnName);
                    field.set(entity, value);
                } else if (field.getType() == String.class) {
                    field.set(entity, rs.getString(columnName));
                } else if (field.getType() == Date.class) {
                    Date date = rs.getDate(columnName);
                    field.set(entity, date);
                }
            }
            entities.add(entity);
        }
    }

    private void fillEntity(Class<E> table, E entity, ResultSet rs) throws SQLException, IllegalAccessException {
        Field[] fields = table.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fillField(field, entity, rs, field.getAnnotation(Column.class).name());
            rs.beforeFirst();
        }
    }

    private void fillField(Field field, E entity, ResultSet rs, String fieldName) throws SQLException, IllegalAccessException {
        setField(field, entity, rs, fieldName);
    }

    private void setField(Field field, E entity, ResultSet rs, String fieldName) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        while (rs.next()) {
            if (field.getType() == int.class) {
                int value = rs.getInt(fieldName);
                field.set(entity, value);
            }
            if (field.getType() == String.class) {
                field.set(entity, rs.getString(fieldName));
            }
            if (field.getType() == Date.class) {
                field.set(entity, rs.getDate(fieldName));
            }
        }
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        // SELECT * FROM table
        // LIMIT 1;
        String query = "SELECT * FROM " + table.getSimpleName().toLowerCase() + "s" +
                System.lineSeparator() +
                System.lineSeparator() +
                "LIMIT 1";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        Constructor<E> constructor = (Constructor<E>) table.getDeclaredConstructor();
        E entity = constructor.newInstance();
        fillEntity(table, entity, rs);
        return entity;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // SELECT * FROM table
        //       WHERE where
        //     LIMIT 1;
        String query = "SELECT * FROM " + table.getSimpleName().toLowerCase() + "s" +
                System.lineSeparator() +
                "WHERE " + where +
                System.lineSeparator() +
                "LIMIT 1";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        Constructor<E> constructor = (Constructor<E>) table.getDeclaredConstructor();
        E entity = constructor.newInstance();
        fillEntity(table, entity, rs);
        return entity;
    }

    @Override
    public boolean checkIfDatabaseExists() throws SQLException {
//        SELECT table_name
//        FROM information_schema.tables
//        WHERE table_schema = 'test'
//        AND table_name = 'users'
//        LIMIT 1;
        String query = "SELECT table_name\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_schema = 'test' AND table_name = '" + getTableName() + "'";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();
        boolean hasNext = resultSet.next();
        System.out.println();
        return hasNext;
    }


    private void updateTableFields() throws SQLException {
        List<String> tableFieldsNames = getTableFieldsNames();
        List<String> classFieldsNames =
                getFields().stream().
                        filter(field -> field.isAnnotationPresent(Column.class)).
                        filter(field -> !tableFieldsNames.contains(field.getAnnotation(Column.class).name())).
                        map(field -> {
                            String name = field.getAnnotation(Column.class).name();
                            String type = "";
                            type = getFieldType(field, type);
                            return String.format("ADD %s %s", name, type);
                        }).collect(Collectors.toList());
        String query = String.format("" +
                "ALTER TABLE `" + getTableName() + "` " +
                "%s", String.join(", ", classFieldsNames));
        PreparedStatement pstmt = connection.prepareStatement(query);
        System.out.println();
        pstmt.execute();

    }

    private String getFieldType(Field field, String type) {
        if (field.getType() == int.class || field.getType() == Integer.class ||
                field.getType() == long.class || field.getType() == Long.class) {
            type = "INT(11)";
        } else if (field.getType() == String.class) {
            type = "VARCHAR(255)";
        } else if (field.getType() == Date.class) {
            type = "DATE";
        }
        return type;
    }

    private void createTable() throws SQLException {
//        CREATE TABLE departments (
//                id INT(11) PRIMARY KEY AUTO_INCREMENT,
//                department_name VARCHAR(50),
//                company_name VARCHAR(50)
//        );
        List<String> columnNames = getFields().stream().
                filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)).
                map(field -> {
                    String name = field.getAnnotation(Column.class).name();
                    String type = "";
                    type = getFieldType(field, type);
                    return name += " " + type;
                }).collect(Collectors.toList());
        String queryCtreateTable = String.format("" +
                        "CREATE TABLE %s (\n" +
                        "%s INT(11) PRIMARY KEY AUTO_INCREMENT,\n" +
                        "%s" +
                        ")",
                getTableName(),
                getId(klass).getAnnotation(Column.class).name(),
                String.join(",\n", columnNames));
        System.out.println();
        PreparedStatement pstmt = connection.prepareStatement(queryCtreateTable);
        pstmt.execute();
    }

    private Field getId(Class<E> entity) {
        return Arrays.stream(entity.getDeclaredFields()).
                filter(field -> field.isAnnotationPresent(Id.class)).
                findFirst().
                orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        // INSERT INTO tablename () VALUES ();
        String tableName = getTableName();
        List<String> columnsNames = getFields().stream().filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class).name()).collect(Collectors.toList());
        List<String> fildsNames = getFields().stream().filter(field -> field.isAnnotationPresent(Column.class))
                .map(Field::getName).collect(Collectors.toList());
        List<Object> fieldsValues = fildsNames.stream().map(fieldName -> getObject(entity, fieldName)).collect(Collectors.toList());

        String colNames = String.join(",", columnsNames);
        String values = fieldsValues.stream().map(Object::toString).collect(Collectors.joining(","));

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, colNames, values);
        try {
            connection.prepareStatement(query).execute();
            updateID(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateID(E entity) throws IllegalAccessException {
        Field id = this.getId((Class<E>) entity.getClass());
        id.setAccessible(true);
        int value = (int) id.get(entity);
        id.set(entity, ++value);
    }

    private boolean doUpdate(E entity) throws IllegalAccessException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(getTableName()).
                append(System.lineSeparator()).
                append("SET ");

        //UPDATE users
        //   SET age = 5
        //    WHERE id = 5;

        List<String> culumnsAndValues = getFields().stream().
                filter(field -> field.isAnnotationPresent(Column.class)).
                map(field -> {
                    StringBuilder sb02 = new StringBuilder();
                    String columnName = field.getAnnotation(Column.class).name();
                    Object value = getObject(entity, field.getName());
                    sb02.append(columnName).append(" ").append("=").append(" ");
                    sb02.append(String.valueOf(value));
                    return sb02.toString();
                }).collect(Collectors.toList());
        query.append(String.join(", ", culumnsAndValues)).
                append(System.lineSeparator()).
                append(" WHERE id = ");
        Field fieldId = getId(klass);
        fieldId.setAccessible(true);
        int id = (int) fieldId.get(entity);
        query.append(id);
        return connection.prepareStatement(query.toString()).execute();
    }

    private List<Field> getFields() {
        return Arrays.stream(klass.getDeclaredFields()).collect(Collectors.toList());
    }

    private String getTableName() {

        return this.klass.getSimpleName().toLowerCase() + "s";

    }

    private Object getObject(E entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object obj = field.get(entity);
            if (obj instanceof String) {
                return "'" + obj + "'";
            }
            if (obj instanceof Date) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(obj);
                return "'" + date + "'";
            }
            return obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <E> boolean checkIfClassHasNewFields(Class<E> klass) throws SQLException {
        List<String> tableColumnNames = getTableFieldsNames();
        List<String> classFieldNames = getFields().stream().
                filter(field -> field.isAnnotationPresent(Column.class)).
                map(field -> field.getAnnotation(Column.class).name()).collect(Collectors.toList());
        System.out.println();
        return classFieldNames.size() > tableColumnNames.size();
    }

    private List<String> getTableFieldsNames() throws SQLException {
        String query = "" +
                "SELECT COLUMN_NAME FROM information_schema.columns " +
                "WHERE table_schema = 'test' AND table_name = '" + getTableName() + "';";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        List<String> tableColumnNames = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            tableColumnNames.add(name);
        }
        return tableColumnNames;
    }

    public void deleteFromTable(String where) throws SQLException {

        String query = "DELETE FROM " + getTableName() + " \n" +
                "WHERE " + where + "";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.execute();
    }

}
