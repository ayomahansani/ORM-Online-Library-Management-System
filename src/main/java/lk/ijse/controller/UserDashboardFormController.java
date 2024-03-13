package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.impl.UsersBorrowingBooksBOImpl;

import java.io.IOException;

public class UserDashboardFormController {

    @FXML
    private AnchorPane dashboard;

    @FXML
    private Label lblNoOfBorrowedBooks;

    @FXML
    private Label lblNoOfReturnedBooks;

    @FXML
    private Label lblNoOfHaveToReturnBooks;

    private UsersBorrowingBooksBOImpl usersBorrowingBooksBO = new UsersBorrowingBooksBOImpl();

    public void initialize(){
        setLblNoOfBorrowedBooks();
        setLblNoOfReturnedBooks();
        setLblNoOfHaveToReturnBooks();
    }

    private void setLblNoOfBorrowedBooks() {
        int noOfBorrowedBooks = usersBorrowingBooksBO.setCurrentNumberOfAllBorrowedBooks(LoginFormController.email);
        lblNoOfBorrowedBooks.setText(String.valueOf(noOfBorrowedBooks));
    }

    private void setLblNoOfReturnedBooks() {
        int noOfReturnedBooks = usersBorrowingBooksBO.setCurrentNumberOfReturnedBooks(LoginFormController.email);
        lblNoOfReturnedBooks.setText(String.valueOf(noOfReturnedBooks));
    }

    private void setLblNoOfHaveToReturnBooks() {
        int noOfHaveToReturnBooks = usersBorrowingBooksBO.setCurrentNumberOfHaveToReturnBooks(LoginFormController.email);
        lblNoOfHaveToReturnBooks.setText(String.valueOf(noOfHaveToReturnBooks));
    }

    @FXML
    void borrowedBooksViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/borrowed_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Borrowed Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void returnedBooksViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/returned_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Returned Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void haveToReturnViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/pending_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Pending Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
