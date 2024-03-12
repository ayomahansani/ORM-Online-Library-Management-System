package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.bo.custom.impl.UsersBorrowingBooksBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
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
    private UsersBorrowingBooksBOImpl usersBorrowingBooksBO = new UsersBorrowingBooksBOImpl();

    public void initialize(){
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

    @FXML
    void cmbUsersOnAction(ActionEvent event) {

        String userName = cmbUsers.getValue();

        loadUserTransactionHistory(userName);
        setCellValueFactory();
    }

    private void loadUserTransactionHistory(String userName) {

        ObservableList<UserHistoryTm> obList = FXCollections.observableArrayList();

        List<UsersBorrowingBooksDTO> usersHistory = usersBorrowingBooksBO.getUserHistory(userName);

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

        /*ObservableList<UserHistoryTm> obList = FXCollections.observableArrayList();

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

        tblUserBookDetails.setItems(obList);*/

    }

    private void setCellValueFactory() {

        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnOrNot.setCellValueFactory(new PropertyValueFactory<>("returnOrNot"));
    }


}
