package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Admin;

import java.sql.SQLException;

public interface AdminDAO extends CrudDAO<Admin> {

    boolean checkAdminCredential(String email, String password, String password1) throws SQLException;
    boolean checkUserName(String email) throws SQLException;
    boolean updatePassword(String email, String newPassword) throws SQLException;

    //boolean save(Admin admin);
}
