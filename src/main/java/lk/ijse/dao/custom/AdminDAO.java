package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Admin;

import java.sql.SQLException;

public interface AdminDAO extends CrudDAO<Admin> {

    boolean checkAdminCredential(String email, String password) throws SQLException;

    //boolean save(Admin admin);
}
