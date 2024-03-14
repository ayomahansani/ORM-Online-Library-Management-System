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

public class ReturnedBooksViewFormController {

    @FXML
    private AnchorPane returnedBooksViewPage;

    @FXML
    private TableView<UserHistoryTm> tblReturnedBooks;

    @FXML
    private TableColumn<?, ?> colBookTitle;

    @FXML
    private TableColumn<?, ?> colReturnedDate;


    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);


    public void initialize(){
        loadReturnedBooks();
        setCellValueFactory();
    }

    private void loadReturnedBooks() {

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
                }
            }

            tblReturnedBooks.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }


}
