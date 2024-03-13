package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.bo.custom.impl.UsersBorrowingBooksBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.UserHistoryTm;

import java.util.List;

public class ReturnedBooksViewFormController {

    @FXML
    private AnchorPane returnedBooksViewPage;

    @FXML
    private TableView<UserHistoryTm> tblReturnedBooks;

    @FXML
    private TableColumn<?, ?> colBookTitle;

    @FXML
    private TableColumn<?, ?> colReturnedDate;

    private UsersBorrowingBooksBOImpl usersBorrowingBooksBO = new UsersBorrowingBooksBOImpl();
    private UserBOImpl userBO = new UserBOImpl();

    public void initialize(){
        loadReturnedBooks();
        setCellValueFactory();
    }

    private void loadReturnedBooks() {

        ObservableList<UserHistoryTm> obList = FXCollections.observableArrayList();

        String username = userBO.getName(LoginFormController.email);

        List<UsersBorrowingBooksDTO> usersHistory = usersBorrowingBooksBO.getUserHistory(username);

        for(UsersBorrowingBooksDTO historyDto : usersHistory){

            BookDTO bookDTO = historyDto.getBookDTO();
            String bookTitle = bookDTO.getBook_title();

            boolean isReturn = historyDto.is_return();

            if(isReturn == true){
                obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(), "Returned"));
            }
        }

        tblReturnedBooks.setItems(obList);
    }

    private void setCellValueFactory() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }


}