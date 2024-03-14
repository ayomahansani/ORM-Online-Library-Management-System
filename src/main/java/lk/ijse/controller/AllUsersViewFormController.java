package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.UserDTO;
import lk.ijse.tm.UserTm;

import java.sql.SQLException;
import java.util.List;

public class AllUsersViewFormController {

    @FXML
    private AnchorPane allUsersViewPage;

    @FXML
    private TableView<UserTm> tblAllUsers;

    @FXML
    private TableColumn<?, ?> colUsername;

    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize() {
        setCellValueFactory();
        loadAllBranches();
    }

    private void loadAllBranches() {

        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        try{

            List<UserDTO> allUsers = userBO.getAllUsers();

            for(UserDTO dto : allUsers){
                obList.add(new UserTm(dto.getUser_email(), dto.getUser_name(), dto.getUser_password()));
            }

            tblAllUsers.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colUsername.setCellValueFactory(new PropertyValueFactory<>("user_name"));
    }

}
