package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {

    boolean saveUserSignUp(UserDTO userDTO) throws SQLException;
    int setCurrentNumberOfUsers() throws SQLException;
    boolean checkUserCredential(String email, String password) throws SQLException;
    String getName(String email) throws SQLException;
    boolean updateUserDetails(String name, String newName, String newEmail, String newPw) throws SQLException;
    List<UserDTO> getAllUsers() throws SQLException;
    boolean deleteAccount(String email) throws SQLException;
    String getUserBranch(String email) throws SQLException;
    UserDTO getUser(String email) throws SQLException;
}
