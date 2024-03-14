package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UsersBorrowingBooksDTO;

import java.sql.SQLException;

public interface TransactionBO extends SuperBO {

    String generateNextTransactionId() throws SQLException;
    boolean isBorrowBook(UsersBorrowingBooksDTO dto) throws SQLException;
    boolean updateIsReturn(UsersBorrowingBooksDTO dto) throws SQLException;
}
