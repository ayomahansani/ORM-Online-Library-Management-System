package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserDashboardFormController {

    @FXML
    private AnchorPane dashboard;

    @FXML
    private Label lblNoOfBorrowedBooks;

    @FXML
    private Label lblNoOfReturnedBooks;

    @FXML
    private Label lblNoOfHaveToReturnBooks;

    public void initialize(){
        setLblNoOfBorrowedBooks();
        setLblNoOfReturnedBooks();
        setLblNoOfHaveToReturnBooks();
    }

    private void setLblNoOfHaveToReturnBooks() {

    }

    private void setLblNoOfReturnedBooks() {

    }

    private void setLblNoOfBorrowedBooks() {

    }

    @FXML
    void borrowedBooksViewOnAction(ActionEvent event) {

    }

    @FXML
    void haveToReturnViewOnAction(ActionEvent event) {

    }

    @FXML
    void returnedBooksViewOnAction(ActionEvent event) {

    }

}
