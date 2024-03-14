package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;

import java.sql.SQLException;
import java.util.List;

public interface QueryBO extends SuperBO {

    List<UsersBorrowingBooksDTO> getUserHistory(String userName) throws SQLException;
    List<UserDTO> loadUsersNotReturnedYet() throws SQLException;

}
