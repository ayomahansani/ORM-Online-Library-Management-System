package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BranchBO;
import lk.ijse.bo.custom.impl.BranchBOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.tm.BranchTm;

import java.sql.SQLException;
import java.util.List;

public class AllBranchesViewFormController {

    @FXML
    private AnchorPane allBranchesViewPage;

    @FXML
    private TableView<BranchTm> tblAllBranches;

    @FXML
    private TableColumn<?, ?> colBranchName;

    private BranchBO branchBO = (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BRANCH);

    public void initialize() {
        setCellValueFactory();
        loadAllBranches();
    }

    private void loadAllBranches() {

        ObservableList<BranchTm> obList = FXCollections.observableArrayList();

        try{
            List<BranchDTO> allBranches = branchBO.getAllBranches();

            for(BranchDTO dto : allBranches){
                obList.add(new BranchTm(dto.getBranch_id(), dto.getBranch_address(), dto.getBranch_telephone()));
            }

            tblAllBranches.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colBranchName.setCellValueFactory(new PropertyValueFactory<>("branch_address"));
    }

}
