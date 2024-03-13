package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.TransactionBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.bo.custom.impl.UsersBorrowingBooksBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.MyHistoryTm;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowingHistoryFormController {

    @FXML
    private AnchorPane myHistoryPage;

    @FXML
    private TableView<MyHistoryTm> tblMyTransactionHistory;

    @FXML
    private TableColumn<?, ?> colTransactionId;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturnOrNot;

    @FXML
    private TableColumn<?, ?> colReturnBtn;

    private UsersBorrowingBooksBOImpl usersBorrowingBooksBO = new UsersBorrowingBooksBOImpl();
    private TransactionBOImpl transactionBO = new TransactionBOImpl();
    private UserBOImpl userBO = new UserBOImpl();


    public void initialize(){
        loadAllTransactions();
        setCellValueFactory();
    }

    private void loadAllTransactions() {

        ObservableList<MyHistoryTm> obList = FXCollections.observableArrayList();

        String username = userBO.getName(LoginFormController.email);

        List<UsersBorrowingBooksDTO> myHistory = usersBorrowingBooksBO.getUserHistory(username);

        for(UsersBorrowingBooksDTO historyDto : myHistory){

            BookDTO bookDTO = historyDto.getBookDTO();
            String bookTitle = bookDTO.getBook_title();
            boolean isReturn = historyDto.is_return();

            Button returnBtn = new Button("Return Now");

            setReturnBtnAction(returnBtn, historyDto);
            returnBtn.setCursor(Cursor.HAND);

            if(isReturn == true){
                returnBtn.setDisable(true);
                obList.add(new MyHistoryTm(historyDto.getTransaction_id(),bookTitle,historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(),"Returned",returnBtn));
            }else {
                obList.add(new MyHistoryTm(historyDto.getTransaction_id(),bookTitle,historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(),"Not Returned",returnBtn));
            }
        }

        tblMyTransactionHistory.setItems(obList);

    }

    private void setReturnBtnAction(Button returnBtn, UsersBorrowingBooksDTO dto) {

        returnBtn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to return?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                boolean updateIsReturnAndAvailabilityStatus = transactionBO.updateIsReturn(dto);

                if(updateIsReturnAndAvailabilityStatus){

                    initialize();
                    new Alert(Alert.AlertType.INFORMATION, "Book Returned Successfully!").show();
                }
            }
        });
    }

    private void setCellValueFactory() {

        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnOrNot.setCellValueFactory(new PropertyValueFactory<>("returnOrNot"));
        colReturnBtn.setCellValueFactory(new PropertyValueFactory<>("returnBtn"));
    }

}
