package lk.ijse.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.QueryBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.controller.both.LoginFormController;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.UserHistoryTm;

import java.sql.SQLException;
import java.util.List;

public class BorrowedBooksViewFormController {

    @FXML
    private AnchorPane borrowedBooksViewPage;

    @FXML
    private TableView<UserHistoryTm> tblBorrowedBooks;

    @FXML
    private TableColumn<?, ?> colBookTitle;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);


    public void initialize(){
        loadBorrowedBooks();
        setCellValueFactory();
    }

    private void loadBorrowedBooks() {

        ObservableList<UserHistoryTm> obList = FXCollections.observableArrayList();

        try{

            String username = userBO.getName(LoginFormController.email);

            List<UsersBorrowingBooksDTO> usersHistory = queryBO.getUserHistory(username);

            for(UsersBorrowingBooksDTO historyDto : usersHistory){

                BookDTO bookDTO = historyDto.getBookDTO();
                String bookTitle = bookDTO.getBook_title();

                boolean isReturn = historyDto.is_return();

                if(isReturn == true){
                    obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(), "Returned"));
                }else {
                    obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(), "Not Returned"));
                }

            }

            tblBorrowedBooks.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
    }

}
