package app.contracts;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {
    boolean  persist(E entity) throws IllegalAccessException, SQLException;
    Iterable<E> find (Class<E> table) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException;
    Iterable<E> find (Class<E> table,String where) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;
    E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
    E findFirst(Class<E> table, String where) throws SQLException,
            IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException;
    boolean checkIfDatabaseExists() throws SQLException;
    void deleteFromTable(String where) throws SQLException;

}
