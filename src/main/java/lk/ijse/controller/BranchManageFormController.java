package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.BranchBOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.tm.BranchTm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BranchManageFormController {

    @FXML
    private AnchorPane branchForm;

    @FXML
    private TextField txtBranchId;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtAddress;

    @FXML
    private TableView<BranchTm> tblBranches;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnClear;

    private BranchBOImpl branchBO = new BranchBOImpl();


    public void initialize(){
        setCellValueFactory();
        loadAllBranches();
        tableListener();
        generateNextBranchId();
    }

    private void generateNextBranchId() {
        String branchId = branchBO.generateNextBranchId();    // Using loose coupling
        txtBranchId.setText(branchId);
    }

    private void tableListener() {
        tblBranches.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(BranchTm newValue) {
        txtBranchId.setText(newValue.getBranch_id());
        txtAddress.setText(newValue.getBranch_address());
        txtTel.setText(newValue.getBranch_phone());
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("branch_address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("branch_phone"));
    }

    private void loadAllBranches() {

        ObservableList<BranchTm> obList = FXCollections.observableArrayList();

        List<BranchDTO> allBranches = branchBO.getAllBranches();

        for(BranchDTO dto : allBranches){
            obList.add(new BranchTm(dto.getBranch_id(), dto.getBranch_address(), dto.getBranch_telephone()));
        }

        tblBranches.setItems(obList);
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {

        String branchId = txtBranchId.getText();
        String branchLocation = txtAddress.getText();
        String branchTel = txtTel.getText();

        if(!branchId.isEmpty() && !branchLocation.isEmpty() && !branchTel.isEmpty()){

            BranchDTO branchDTO = new BranchDTO(branchId,branchLocation,branchTel);

            boolean isBranchSaved = branchBO.saveBranch(branchDTO);

            if(isBranchSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "new branch added!").show();
                clearFields();
                initialize();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String branchId = txtBranchId.getText();
        String branchLocation = txtAddress.getText();
        String branchTel = txtTel.getText();

        if(!branchId.isEmpty() && !branchLocation.isEmpty() && !branchTel.isEmpty()){

            BranchDTO branchDTO = new BranchDTO(branchId,branchLocation,branchTel);

            boolean isBranchUpdated = branchBO.updateBranch(branchDTO);

            if(isBranchUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "branch updated!").show();
                clearFields();
                initialize();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
        }
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                if ((Pattern.matches("(Br)[0-9]{3,}", txtBranchId.getText()))) {

                    String id = txtBranchId.getText();

                    boolean isBranchDeleted = branchBO.deleteBranch(id);   // Using loose coupling
                    if (isBranchDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "branch deleted!").show();
                        clearFields();
                        initialize();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Id!").show();
                }
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtBranchId.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    @FXML
    void txtBranchSearchOnAction(ActionEvent event) {

        if ((Pattern.matches("[Br][0-9]{3,}", txtBranchId.getText()))) {

            String id = txtBranchId.getText();

            BranchDTO branchDTO = branchBO.searchBranch(id);

            if(branchDTO != null){
                txtBranchId.setText(branchDTO.getBranch_id());
                txtAddress.setText(branchDTO.getBranch_address());
                txtTel.setText(branchDTO.getBranch_telephone());
            }else {
                new Alert(Alert.AlertType.INFORMATION, "branch not found").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
        }
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {
        btnAddOnAction(event);
    }

}
