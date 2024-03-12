package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

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

    public boolean updateUserDetails(String name, String newName, String newEmail, String newPw) {
        return userDAO.updateUserDetails(name,newName,newEmail,newPw);
    }

    public List<UserDTO> getAllUsers() {

        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User user : users){
            Branch branch = user.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            userDTOS.add(new UserDTO(user.getUserEmail(),user.getUserName(),user.getUserPassword(),branchDTO));
        }

        return userDTOS;
    }

    public boolean deleteAccount(String email) {
        return userDAO.delete(email);
    }

    public String getUserBranch(String email) {
        return userDAO.getUserBranch(email);
    }

    public UserDTO getUser(String email) {

        User user = userDAO.getUser(email);
        Branch branch = user.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());

        return new UserDTO(user.getUserEmail(),user.getUserName(),user.getUserPassword(),branchDTO);
    }
}
