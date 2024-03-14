package lk.ijse.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.QueryBO;
import lk.ijse.bo.custom.TransactionBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.controller.both.LoginFormController;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.MyHistoryTm;

import java.sql.SQLException;
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

    private TransactionBO transactionBO = (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TRANSACTION);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);


    public void initialize(){
        loadAllTransactions();
        setCellValueFactory();
    }

    private void loadAllTransactions() {

        ObservableList<MyHistoryTm> obList = FXCollections.observableArrayList();

        try{

            String username = userBO.getName(LoginFormController.email);

            List<UsersBorrowingBooksDTO> myHistory = queryBO.getUserHistory(username);

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

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setReturnBtnAction(Button returnBtn, UsersBorrowingBooksDTO dto) {

        returnBtn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            if(LocalDate.now().isBefore(dto.getDue_date())){

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "You still have days to return.\nAre you sure to return?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    try{

                        boolean updateIsReturnAndAvailabilityStatus = transactionBO.updateIsReturn(dto);

                        if(updateIsReturnAndAvailabilityStatus){

                            initialize();
                            new Alert(Alert.AlertType.INFORMATION, "Book Returned Successfully!").show();
                        }

                    }catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }else {

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Your due date has passed!\nAre you sure to return?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    try{

                        boolean updateIsReturnAndAvailabilityStatus = transactionBO.updateIsReturn(dto);

                        if(updateIsReturnAndAvailabilityStatus){

                            initialize();
                            new Alert(Alert.AlertType.INFORMATION, "Book Returned Successfully!").show();
                        }

                    }catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

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
