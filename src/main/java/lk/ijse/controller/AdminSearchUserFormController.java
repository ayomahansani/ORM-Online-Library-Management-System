package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.AdminSearchUserHistoryBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.BookTm;
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
    private AdminSearchUserHistoryBOImpl adminSearchUserHistoryBO = new AdminSearchUserHistoryBOImpl();

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

        ObservableList<UserHistoryTm> obList = FXCollections.observableArrayList();

        List<UsersBorrowingBooksDTO> usersHistory = adminSearchUserHistoryBO.getUserHistory();

        for(UsersBorrowingBooksDTO historyDto : usersHistory){

            BookDTO bookDTO = historyDto.getBookDTO();
            String bookTitle = bookDTO.getBook_title();

            boolean isReturn = historyDto.is_return();

            if(isReturn == true){
                obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getReturn_date(), "Returned"));
            }else {
                obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getReturn_date(), "Not Returned"));
            }

        }

        tblUserBookDetails.setItems(obList);

    }

    private void tableListener() {
        tblUserBookDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setCellValueFactory() {

    }

    @FXML
    void cmbUsersOnAction(ActionEvent event) {

    }

}
