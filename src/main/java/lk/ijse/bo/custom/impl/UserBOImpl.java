package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

public class UserBOImpl {

    private UserDAOImpl userDAO = new UserDAOImpl();
    public boolean saveUserSignUp(UserDTO userDTO) {
        BranchDTO branchDTO = userDTO.getBranchDTO();
        Branch branch = new Branch(branchDTO.getBranch_id(),branchDTO.getBranch_address(), branchDTO.getBranch_telephone(),null,null);

        return userDAO.save(new User(userDTO.getUser_email(),userDTO.getUser_name(),userDTO.getUser_password(),branch,null));
    }

    public int setCurrentNumberOfUsers() {
        return userDAO.setCurrentNumber();
    }

    public boolean checkUserCredential(String email, String password) {
        return userDAO.checkUserCredential(email, password);
    }

    public String getName(String email) {
        return userDAO.getName(email);
    }

    public boolean updateUserDetails(String name, String newEmail, String newPw) {
        return userDAO.updateUserDetails(name,newEmail,newPw);
    }
}
