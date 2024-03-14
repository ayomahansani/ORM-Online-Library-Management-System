package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface UsersBorrowingBooksBO extends SuperBO {

    int setCurrentNumberOfAllBorrowedBooks(String email) throws SQLException;
    int setCurrentNumberOfReturnedBooks(String email) throws SQLException;
    int setCurrentNumberOfHaveToReturnBooks(String email) throws SQLException;
}
