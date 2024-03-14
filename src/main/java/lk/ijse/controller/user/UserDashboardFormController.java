package lk.ijse.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UsersBorrowingBooksBO;
import lk.ijse.controller.both.LoginFormController;

import java.io.IOException;
import java.sql.SQLException;

public class UserDashboardFormController {

    @FXML
    private AnchorPane dashboard;

    @FXML
    private Label lblNoOfBorrowedBooks;

    @FXML
    private Label lblNoOfReturnedBooks;

    @FXML
    private Label lblNoOfHaveToReturnBooks;

    private UsersBorrowingBooksBO usersBorrowingBooksBO = (UsersBorrowingBooksBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USERS_BORROWING_BOOKS);

    public void initialize(){
        setLblNoOfBorrowedBooks();
        setLblNoOfReturnedBooks();
        setLblNoOfHaveToReturnBooks();
    }

    private void setLblNoOfBorrowedBooks() {
        try{

            int noOfBorrowedBooks = usersBorrowingBooksBO.setCurrentNumberOfAllBorrowedBooks(LoginFormController.email);
            lblNoOfBorrowedBooks.setText(String.valueOf(noOfBorrowedBooks));

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfReturnedBooks() {
        try{

            int noOfReturnedBooks = usersBorrowingBooksBO.setCurrentNumberOfReturnedBooks(LoginFormController.email);
            lblNoOfReturnedBooks.setText(String.valueOf(noOfReturnedBooks));

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfHaveToReturnBooks() {
        try{

            int noOfHaveToReturnBooks = usersBorrowingBooksBO.setCurrentNumberOfHaveToReturnBooks(LoginFormController.email);
            lblNoOfHaveToReturnBooks.setText(String.valueOf(noOfHaveToReturnBooks));

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void borrowedBooksViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/user/borrowed_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Borrowed Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void returnedBooksViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/user/returned_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Returned Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void haveToReturnViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/user/pending_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Pending Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
