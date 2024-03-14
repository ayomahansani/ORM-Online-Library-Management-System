package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUserSignUp(UserDTO userDTO) throws SQLException {
        BranchDTO branchDTO = userDTO.getBranchDTO();
        Branch branch = new Branch(branchDTO.getBranch_id(),branchDTO.getBranch_address(), branchDTO.getBranch_telephone(),null,null);

        return userDAO.save(new User(userDTO.getUser_email(),userDTO.getUser_name(),userDTO.getUser_password(),branch,null));
    }

    @Override
    public int setCurrentNumberOfUsers() throws SQLException {
        return userDAO.setCurrentNumber();
    }

    @Override
    public boolean checkUserCredential(String email, String password) throws SQLException {
        return userDAO.checkUserCredential(email, password);
    }

    @Override
    public String getName(String email) throws SQLException {
        return userDAO.getName(email);
    }

    @Override
    public boolean updateUserDetails(String name, String newName, String newEmail, String newPw) throws SQLException {
        return userDAO.updateUserDetails(name,newName,newEmail,newPw);
    }

    @Override
    public List<UserDTO> getAllUsers() throws SQLException {

        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User user : users){
            Branch branch = user.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            userDTOS.add(new UserDTO(user.getUserEmail(),user.getUserName(),user.getUserPassword(),branchDTO));
        }

        return userDTOS;
    }

    @Override
    public boolean deleteAccount(String email) throws SQLException {
        return userDAO.delete(email);
    }

    @Override
    public String getUserBranch(String email) throws SQLException {
        return userDAO.getUserBranch(email);
    }

    @Override
    public UserDTO getUser(String email) throws SQLException {

        User user = userDAO.getUser(email);
        Branch branch = user.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());

        return new UserDTO(user.getUserEmail(),user.getUserName(),user.getUserPassword(),branchDTO);
    }
}
