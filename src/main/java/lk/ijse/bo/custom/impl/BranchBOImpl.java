package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.BranchDAOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.entity.Branch;

import java.util.ArrayList;
import java.util.List;

public class BranchBOImpl {

    private BranchDAOImpl branchDAO = new BranchDAOImpl();

    public BranchDTO getBranchByAddress(String branchAddress) {

        Branch branch = branchDAO.getBranchByAddress(branchAddress);
        return new BranchDTO(branch.getBranchId(),branch.getBranchAddress(),branch.getBranchTelephone());
    }

    public List<BranchDTO> getAllBranches() {

        List<Branch> branches = branchDAO.getAll();
        List<BranchDTO> branchDTOS = new ArrayList<>();

        for(Branch b : branches){
            branchDTOS.add(new BranchDTO(b.getBranchId(),b.getBranchAddress(),b.getBranchTelephone()));
        }

        return branchDTOS;
    }

    public int setCurrentNumberOfBranches() {
        return branchDAO.setCurrentNumber();
    }

    public boolean saveBranch(BranchDTO dto) {
        return branchDAO.save(new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null));
    }

    public boolean updateBranch(BranchDTO dto) {
        return branchDAO.update(new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null));
    }

    public boolean deleteBranch(String id) {
        return branchDAO.delete(id);
    }

    public BranchDTO searchBranch(String branchAddress) {

        Branch branch = branchDAO.search(branchAddress);
        return new BranchDTO(branch.getBranchId(),branch.getBranchAddress(), branch.getBranchTelephone());
    }

    public String generateNextBranchId() {
        return branchDAO.generateNextId();
    }

}
