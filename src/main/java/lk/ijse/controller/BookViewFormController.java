package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.impl.TransactionBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;

import java.time.LocalDate;

public class BookViewFormController {

    @FXML
    private AnchorPane bookViewPage;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label lblIsAvailable;

    @FXML
    private Label lblTransactionId;

    @FXML
    private Button btnBorrow;

    @FXML
    private JFXComboBox<String> cmbSelectDueDate;

    @FXML
    private Label lblDueDate;

    private TransactionBOImpl transactionBO = new TransactionBOImpl();
    private UserBOImpl userBO = new UserBOImpl();

    private UserBorrowingBooksFormController userBorrowingBooksFormController = new UserBorrowingBooksFormController();

    public void initialize(){
        generateNextTransactionId();
        setBookDetails();
        setValuesToCombobox();
    }

    private void generateNextTransactionId() {
        String transactionId = transactionBO.generateNextTransactionId();    // Using loose coupling
        lblTransactionId.setText(transactionId);
    }

    private void setBookDetails() {

        BookDTO bookDTO = UserBorrowingBooksFormController.bookDTO;

        lblTitle.setText(bookDTO.getBook_title());
        lblAuthor.setText(bookDTO.getBook_author());

        if(bookDTO.isAvailability_status() == true){
            lblIsAvailable.setText("Available");
        }else {
            lblIsAvailable.setText("Not Available");
            btnBorrow.setDisable(true);
        }

    }

    private void setValuesToCombobox() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("7 days later");
        obList.add("14 days later");
        obList.add("21 days later");

        cmbSelectDueDate.setItems(obList);
    }

    @FXML
    void btnBorrowOnAction(ActionEvent event) {

        String value = cmbSelectDueDate.getValue();
        String transactionId = lblTransactionId.getText();
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = LocalDate.parse(lblDueDate.getText());

        if(!value.isEmpty()){

            BookDTO bookDTO = UserBorrowingBooksFormController.bookDTO;

            UserDTO userDTO = userBO.getUser(LoginFormController.email);

            UsersBorrowingBooksDTO usersBorrowingBooksDTO = new UsersBorrowingBooksDTO(transactionId, borrowDate, dueDate, null,false, userDTO, bookDTO);

            boolean isBorrow = transactionBO.isBorrowBook(usersBorrowingBooksDTO);

            if(isBorrow){
                new Alert(Alert.AlertType.CONFIRMATION, "Borrow Success!").show();

                // Close the pop up
                Stage stage = (Stage) this.bookViewPage.getScene().getWindow();
                stage.close();

                //userBorrowingBooksFormController.initialize();

            }

        }else {
            new Alert(Alert.AlertType.INFORMATION, "Please select the time period").show();
        }
    }

    @FXML
    void cmbSelectDueDateOnAction(ActionEvent event) {

        LocalDate currentDate = LocalDate.now();

        String value = cmbSelectDueDate.getValue();

        if(value.equals("7 days later")){
            LocalDate dueDate = currentDate.plusDays(7);
            lblDueDate.setText(String.valueOf(dueDate));
        } else if (value.equals("14 days later")) {
            LocalDate dueDate = currentDate.plusDays(14);
            lblDueDate.setText(String.valueOf(dueDate));
        } else {
            LocalDate dueDate = currentDate.plusDays(21);
            lblDueDate.setText(String.valueOf(dueDate));
        }
    }

}
