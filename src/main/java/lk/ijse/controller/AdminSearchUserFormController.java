package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.UserDTO;
import lk.ijse.tm.UserHistoryTm;

import java.util.List;

public class AdminSearchUserFormController {

    @FXML
    private AnchorPane userHistoryForm;

    @FXML
    private TableView<UserHistoryTm> tblUserBookDetails;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturnOrNot;

    @FXML
    private JFXComboBox<String> cmbUsers;

    private UserBOImpl userBO = new UserBOImpl();

    public void initialize(){
        loadUserTransactionHistory();
        setCellValueFactory();
        tableListener();
        loadAllUsers();
    }

    private void loadAllUsers() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        List<UserDTO> userDTOS = userBO.getAllUsers();

        for(UserDTO dto : userDTOS){
            obList.add(dto.getUser_name());
        }

        cmbUsers.setItems(obList);
    }

    private void loadUserTransactionHistory() {

    }

    private void tableListener() {

    }

    private void setCellValueFactory() {

    }

    @FXML
    void cmbUsersOnAction(ActionEvent event) {

    }

}
