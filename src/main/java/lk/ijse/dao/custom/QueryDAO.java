package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<Object[]> getUserHistory(String userName) throws SQLException;
    List<Object[]> loadUsersNotReturnedYet() throws SQLException;
}
