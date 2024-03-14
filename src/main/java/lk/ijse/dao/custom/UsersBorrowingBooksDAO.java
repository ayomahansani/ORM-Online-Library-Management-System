package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;

public interface UsersBorrowingBooksDAO extends SuperDAO {

    int setCurrentNumber(String email) throws SQLException;
    int setReturnedCurrentNumber(String email) throws SQLException;
    int setPendingCurrentNumber(String email) throws SQLException;
}
