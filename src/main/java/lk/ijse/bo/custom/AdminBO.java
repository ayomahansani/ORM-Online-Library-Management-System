package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.AdminDTO;

import java.sql.SQLException;

public interface AdminBO extends SuperBO {

    boolean saveAdminSignUp(AdminDTO adminDTO) throws SQLException;
    boolean checkAdminCredential(String email, String password, String password1) throws SQLException;
    boolean checkUserName(String email) throws SQLException;
    boolean updatePassword(String email, String newPassword) throws SQLException;
}
