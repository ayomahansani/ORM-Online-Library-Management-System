package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User>{

    /*boolean save(User user);
    boolean delete(String email);
    List<User> getAll();
    int setCurrentNumber();*/

    boolean checkUserCredential(String email, String password) throws SQLException;
    String getName(String email) throws SQLException;
    boolean updateUserDetails(String name, String newName, String newEmail, String newPw) throws SQLException;
    String getUserBranch(String email) throws SQLException;
    User getUser(String email) throws SQLException;
}
