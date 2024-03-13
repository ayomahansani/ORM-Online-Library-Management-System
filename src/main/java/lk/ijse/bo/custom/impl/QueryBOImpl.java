package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.QueryDAOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class QueryBOImpl {

    private QueryDAOImpl queryDAO = new QueryDAOImpl();

    public List<UserDTO> loadUsersNotReturnedYet() {

        List<Object[]> objectsList = queryDAO.loadUsersNotReturnedYet();

        List<UserDTO> userDTOS = new ArrayList<>();

        for(Object[] object : objectsList){

            Branch branch = (Branch) object[3];
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());

            userDTOS.add(new UserDTO(
                    (String) object[0],
                    (String) object[1],
                    (String) object[2],
                    branchDTO
            ));
        }

        return userDTOS;
    }
}
