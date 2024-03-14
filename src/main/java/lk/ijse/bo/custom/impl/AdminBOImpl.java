package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.AdminBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.AdminDAO;
import lk.ijse.dao.custom.impl.AdminDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.AdminDTO;
import lk.ijse.entity.Admin;

import java.sql.SQLException;

public class AdminBOImpl implements AdminBO {

    private AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADMIN);

    @Override
    public boolean saveAdminSignUp(AdminDTO adminDTO) throws SQLException {
        return adminDAO.save(new Admin(adminDTO.getAdmin_email(),adminDTO.getAdmin_name(),adminDTO.getAdmin_password()));
    }

    @Override
    public boolean checkAdminCredential(String email, String password, String password1) throws SQLException {
        return adminDAO.checkAdminCredential(email, password, password1);
    }

    @Override
    public boolean checkUserName(String email) throws SQLException {
        return adminDAO.checkUserName(email);
    }

    @Override
    public boolean updatePassword(String email, String newPassword) throws SQLException{
        return adminDAO.updatePassword(email,newPassword);
    }
}
