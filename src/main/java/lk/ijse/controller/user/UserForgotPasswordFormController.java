package lk.ijse.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;

import java.util.regex.Pattern;

public class UserForgotPasswordFormController {

    @FXML
    private AnchorPane userResetPwPage;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblText;

    @FXML
    private Button btnResetPassword;

    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    private boolean validate() {

        //validate email
        String email = txtUsername.getText();
        boolean matchesEmail = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{10,}", email);
        if(!matchesEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid User Name").show();
            return false;
        }

        //validate password
        String password = txtPassword.getText();
        boolean matchesPassword = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{8,}", password);
        if(!matchesPassword){
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            return false;
        }

        return true;
    }


    @FXML
    void btnResetPasswordOnAction(ActionEvent event) {

        boolean validate = validate();

        if(validate){

            String email = txtUsername.getText();

            try {

                boolean isCheck = userBO.checkUserName(email); // Using loose coupling

                if (isCheck) {
                    String newPassword = txtPassword.getText();

                    boolean isSet = userBO.updatePassword(email,newPassword);  // Using loose coupling

                    if (isSet) {

                        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/both/login_form.fxml"));
                        Scene scene = new Scene(rootNode);
                        userResetPwPage.getChildren().clear();
                        Stage primaryStage = (Stage) userResetPwPage.getScene().getWindow();
                        primaryStage.setScene(scene);
                        primaryStage.centerOnScreen();
                        primaryStage.setTitle("Login Form");

                        new Alert(Alert.AlertType.CONFIRMATION, "Password Reset successful!!").show();
                    }
                } else {
                    lblText.setText("Username is incorrect");
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnResetPasswordOnAction(event);
    }

}
