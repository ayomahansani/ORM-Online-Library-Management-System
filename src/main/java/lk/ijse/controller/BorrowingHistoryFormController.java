package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.tm.MyHistoryTm;

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
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturnOrNot;

    @FXML
    private TableColumn<?, ?> colReturnBtn;

    public void initialize(){
        loadAllTransactions();
        setCellValueFactory();
    }


    private void loadAllTransactions() {

    }

    private void setCellValueFactory() {

    }

}
