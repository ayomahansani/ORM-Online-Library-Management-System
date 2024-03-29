package lk.ijse.controller.both;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormController {

    @FXML
    private AnchorPane signUpForm;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnAdmin;

    @FXML
    void btnAdminOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/admin/admin_register_form.fxml"));
        Scene scene = new Scene(rootNode);
        signUpForm.getChildren().clear();
        Stage primaryStage = (Stage) signUpForm.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Admin Register Form");
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user/user_register_form.fxml"));
        Scene scene = new Scene(rootNode);
        signUpForm.getChildren().clear();
        Stage primaryStage = (Stage) signUpForm.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("User Register Form");
    }

}
