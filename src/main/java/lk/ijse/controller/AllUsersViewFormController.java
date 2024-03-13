package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.tm.BranchTm;
import lk.ijse.tm.UserTm;

import java.util.List;

public class AllUsersViewFormController {

    @FXML
    private AnchorPane allUsersViewPage;

    @FXML
    private TableView<UserTm> tblAllUsers;

    @FXML
    private TableColumn<?, ?> colUsername;

    private UserBOImpl userBO = new UserBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllBranches();
    }

    private void loadAllBranches() {

        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        List<UserDTO> allUsers = userBO.getAllUsers();

        for(UserDTO dto : allUsers){
            obList.add(new UserTm(dto.getUser_email(), dto.getUser_name(), dto.getUser_password()));
        }

        tblAllUsers.setItems(obList);
    }

    private void setCellValueFactory() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("user_name"));
    }
    
}
