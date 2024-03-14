package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BranchDTO;

import java.sql.SQLException;
import java.util.List;

public interface BranchBO extends SuperBO {

    BranchDTO getBranchByAddress(String branchAddress) throws SQLException;
    List<BranchDTO> getAllBranches() throws SQLException;
    int setCurrentNumberOfBranches() throws SQLException;
    boolean saveBranch(BranchDTO dto) throws SQLException;
    boolean updateBranch(BranchDTO dto) throws SQLException;
    boolean deleteBranch(String id) throws SQLException;
    BranchDTO searchBranch(String branchAddress) throws SQLException;
    String generateNextBranchId() throws SQLException;
}
