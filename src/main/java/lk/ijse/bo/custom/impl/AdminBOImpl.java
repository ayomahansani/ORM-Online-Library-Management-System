package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.AdminDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.AdminDTO;
import lk.ijse.entity.Admin;

public class AdminBOImpl {

    private AdminDAOImpl adminDAO = new AdminDAOImpl();

    public boolean saveAdminSignUp(AdminDTO adminDTO) {
        return adminDAO.save(new Admin(adminDTO.getAdmin_email(),adminDTO.getAdmin_name(),adminDTO.getAdmin_password()));
    }

    public boolean checkAdminCredential(String email, String password) {
        return adminDAO.checkAdminCredentia(email, password);
    }
}
