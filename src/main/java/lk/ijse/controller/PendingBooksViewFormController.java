package lk.ijse.controller;

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
import lk.ijse.bo.custom.impl.QueryBOImpl;
import lk.ijse.bo.custom.impl.TransactionBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.MyHistoryTm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PendingBooksViewFormController {

    @FXML
    private AnchorPane pendingBooksViewForm;

    @FXML
    private TableView<MyHistoryTm> tblPendingBooks;

    @FXML
    private TableColumn<?, ?> colBookTitle;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colReturnBtn;

    @FXML
    private TableColumn<?, ?> colReturnDate;


    private TransactionBO transactionBO = (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TRANSACTION);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);


    public void initialize(){
        loadPendingBooks();
        setCellValueFactory();
    }

    private void loadPendingBooks() {

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

                if(isReturn == false){
                    obList.add(new MyHistoryTm(historyDto.getTransaction_id(),bookTitle,historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(),"Not Returned",returnBtn));
                }
            }

            tblPendingBooks.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setReturnBtnAction(Button returnBtn, UsersBorrowingBooksDTO dto) {

        returnBtn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to return?", yes, no).showAndWait();

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
        });
    }

    private void setCellValueFactory() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnBtn.setCellValueFactory(new PropertyValueFactory<>("returnBtn"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

}
