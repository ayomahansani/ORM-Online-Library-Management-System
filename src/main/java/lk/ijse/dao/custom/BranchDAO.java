package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Branch;

import java.sql.SQLException;


public interface BranchDAO extends CrudDAO<Branch> {

    /*boolean save(Branch branch);
    boolean update(Branch branch);
    boolean delete(String id);
    Branch search(String branchAddress);
    List<Branch> getAll();
    String generateNextId();
    String splitId(String currentBranchId);
    int setCurrentNumber();*/

    Branch getBranchByAddress(String branchAddress) throws SQLException;

}
