package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.impl.BookBOImpl;
import lk.ijse.bo.custom.impl.BranchBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;

import java.io.IOException;

public class AdminDashboardFormController {

    @FXML
    private AnchorPane dashboard;

    @FXML
    private Label lblNoOfUsers;

    @FXML
    private Label lblNoOfBooks;

    @FXML
    private Label lblNoOfBranches;

    private BranchBOImpl branchBO = new BranchBOImpl();
    private BookBOImpl bookBO = new BookBOImpl();
    private UserBOImpl userBO = new UserBOImpl();


    public void initialize(){
        setLblNoOfUsers();
        setLblNoOfBooks();
        setLblNoOfBranches();
    }

    private void setLblNoOfBranches() {
        int noOfBranches = branchBO.setCurrentNumberOfBranches();
        lblNoOfBranches.setText(String.valueOf(noOfBranches));
    }

    private void setLblNoOfBooks() {
        int noOfBooks = bookBO.setCurrentNumberOfBooks();
        lblNoOfBooks.setText(String.valueOf(noOfBooks));
    }

    private void setLblNoOfUsers() {
        int noOfUsers = userBO.setCurrentNumberOfUsers();
        lblNoOfUsers.setText(String.valueOf(noOfUsers));
    }

    @FXML
    void goBooksPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/book_manage_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Book Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goBranchesPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/branch_manage_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Branch Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goUsersPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/user_borrowing_books_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Book Borrow Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void booksViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/all_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("All Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void branchesViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/all_branches_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("All Branches");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void usersViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/all_users_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("All Users");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
