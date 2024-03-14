package lk.ijse.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BookBO;
import lk.ijse.bo.custom.BranchBO;
import lk.ijse.bo.custom.UserBO;

import java.io.IOException;
import java.sql.SQLException;

public class AdminDashboardFormController {

    @FXML
    private AnchorPane dashboard;

    @FXML
    private Label lblNoOfUsers;

    @FXML
    private Label lblNoOfBooks;

    @FXML
    private Label lblNoOfBranches;

    private BranchBO branchBO = (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BRANCH);
    private BookBO bookBO = (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    public void initialize() throws SQLException {
        setLblNoOfUsers();
        setLblNoOfBooks();
        setLblNoOfBranches();
    }

    private void setLblNoOfBranches(){
        try{
            int noOfBranches = branchBO.setCurrentNumberOfBranches();
            lblNoOfBranches.setText(String.valueOf(noOfBranches));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfBooks() {
        try{
            int noOfBooks = bookBO.setCurrentNumberOfBooks();
            lblNoOfBooks.setText(String.valueOf(noOfBooks));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfUsers() {
        try{
            int noOfUsers = userBO.setCurrentNumberOfUsers();
            lblNoOfUsers.setText(String.valueOf(noOfUsers));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goBooksPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/admin/book_manage_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Book Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goBranchesPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/admin/branch_manage_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Branch Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goUsersPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/user/user_borrowing_books_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Book Borrow Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void booksViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/admin/all_books_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("All Books");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void branchesViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/admin/all_branches_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("All Branches");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void usersViewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/admin/all_users_view_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("All Users");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
