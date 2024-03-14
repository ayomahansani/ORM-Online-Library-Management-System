package lk.ijse.dao;


import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {  // Created for less boilerplate codes --> gather same methods in one place

    boolean save(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(String id) throws SQLException;
    T search(String name) throws SQLException;
    List<T> getAll() throws SQLException;
    String generateNextId() throws SQLException;
    String splitId(String currentId) throws SQLException;
    int setCurrentNumber() throws SQLException;

}
