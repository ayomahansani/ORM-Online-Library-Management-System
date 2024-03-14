package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BranchBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BranchDAO;
import lk.ijse.dao.custom.impl.BranchDAOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.entity.Branch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchBOImpl implements BranchBO {

    private BranchDAO branchDAO = (BranchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BRANCH);

    @Override
    public BranchDTO getBranchByAddress(String branchAddress) throws SQLException {

        Branch branch = branchDAO.getBranchByAddress(branchAddress);
        return new BranchDTO(branch.getBranchId(),branch.getBranchAddress(),branch.getBranchTelephone());
    }

    @Override
    public List<BranchDTO> getAllBranches() throws SQLException {

        List<Branch> branches = branchDAO.getAll();
        List<BranchDTO> branchDTOS = new ArrayList<>();

        for(Branch b : branches){
            branchDTOS.add(new BranchDTO(b.getBranchId(),b.getBranchAddress(),b.getBranchTelephone()));
        }

        return branchDTOS;
    }

    @Override
    public int setCurrentNumberOfBranches() throws SQLException {
        return branchDAO.setCurrentNumber();
    }

    @Override
    public boolean saveBranch(BranchDTO dto) throws SQLException {
        return branchDAO.save(new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null));
    }

    @Override
    public boolean updateBranch(BranchDTO dto) throws SQLException {
        return branchDAO.update(new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null));
    }

    @Override
    public boolean deleteBranch(String id) throws SQLException {
        return branchDAO.delete(id);
    }

    @Override
    public BranchDTO searchBranch(String branchAddress) throws SQLException {

        Branch branch = branchDAO.search(branchAddress);

        if(branch != null){
            return new BranchDTO(branch.getBranchId(),branch.getBranchAddress(), branch.getBranchTelephone());
        }

        return null;

    }

    @Override
    public String generateNextBranchId() throws SQLException {
        return branchDAO.generateNextId();
    }

}
