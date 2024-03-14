package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;

public interface TransactionDAO extends SuperDAO {

    String generateNextId() throws SQLException;
    String splitId(String currentTransactionId) throws SQLException;

}
