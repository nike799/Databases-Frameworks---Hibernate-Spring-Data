package app.core;


import app.contracts.DbContext;
import app.entities.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "test";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException, IllegalAccessException, ParseException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Connection connection = getConnection();
        DbContext<User> entityManager = getDbContext(connection, User.class);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();

        User user = new User("nike79", 39, date);

        entityManager.persist(user);
        entityManager.checkIfDatabaseExists();
        //  List<User> users = (List<User>) entityManager.find(User.class);
        //  List<User> users01 = (List<User>) entityManager.find(User.class,"YEAR(registration_date) < 2013");
        //  User user1 = entityManager.findFirst(User.class);
        //  User user1 = entityManager.findFirst(User.class,"id = 1");
        //  entityManager.deleteFromTable("id = 1");
        connection.close();

    }

    private static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        return DriverManager.getConnection(CONNECTION_STRING + DB_NAME, properties);
    }

    private static <T> DbContext<T> getDbContext(Connection connection, Class<T> klass) throws SQLException {
        return new EntityManager<>(connection, klass);
    }
}
