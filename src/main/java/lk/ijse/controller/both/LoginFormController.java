package lk.ijse.controller.both;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.AdminBO;
import lk.ijse.bo.custom.UserBO;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFormController {

    @FXML
    private AnchorPane loginPage;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private JFXComboBox<String> cmbAdminOrUser;

    @FXML
    private Button btnSignupHere;

    @FXML
    private Button btnForgotPassword;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox checkBoxPw;

    private AdminBO adminBO = (AdminBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ADMIN);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public static String email;
    public static String password;


    private boolean validateSignIn() {

        //validate email
        String email = txtUsername.getText();
        boolean matchesEmail = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{10,}", email);
        if(!matchesEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            return false;
        }

        //validate password
        String password = passwordField.getText();
        boolean matchesPassword = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{8,}", password);

        String password1 = passwordField.getText();
        boolean matchesPassword1 = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{8,}", password1);

        if(!matchesPassword || !matchesPassword1){
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            return false;
        }

        return true;
    }


    public void initialize(){
        setValuesToComboBox();
    }

    private void setValuesToComboBox() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Admin");
        obList.add("User");

        cmbAdminOrUser.setItems(obList);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        /*String adminOruser = cmbAdminOrUser.getValue();

        if(adminOruser.equals("Admin")){
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/admin/admin_main_form.fxml"));
            Scene scene = new Scene(rootNode);
            loginPage.getChildren().clear();
            Stage primaryStage = (Stage) loginPage.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Admin Main Form");

        }else{
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user/user_main_form.fxml"));
            Scene scene = new Scene(rootNode);
            loginPage.getChildren().clear();
            Stage primaryStage = (Stage) loginPage.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Admin Main Form");
        }*/

        boolean isValidated = validateSignIn();

        if(isValidated){

            String adminOrUser = cmbAdminOrUser.getValue();
            email = txtUsername.getText();
            password = passwordField.getText();
            String password1 = txtPassword.getText();

            if(!adminOrUser.isEmpty() && !email.isEmpty() && (!password.isEmpty() || !password1.isEmpty())){

                try{

                    if(adminOrUser.equals("Admin")){
                        boolean isMatched = adminBO.checkAdminCredential(email, password, password1);

                        if(isMatched){
                            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/admin/admin_main_form.fxml"));
                            Scene scene = new Scene(rootNode);
                            loginPage.getChildren().clear();
                            Stage primaryStage = (Stage) loginPage.getScene().getWindow();
                            primaryStage.setScene(scene);
                            primaryStage.centerOnScreen();
                            primaryStage.setTitle("Admin Main Form");

                            new Alert(Alert.AlertType.CONFIRMATION, "Login Success!").show();

                        }else {
                            new Alert(Alert.AlertType.CONFIRMATION, "Incorrect Username or Password!").show();
                        }

                    }else {
                        boolean isMatched = userBO.checkUserCredential(email, password, password1);

                        if(isMatched){
                            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user/user_main_form.fxml"));
                            Scene scene = new Scene(rootNode);
                            loginPage.getChildren().clear();
                            Stage primaryStage = (Stage) loginPage.getScene().getWindow();
                            primaryStage.setScene(scene);
                            primaryStage.centerOnScreen();
                            primaryStage.setTitle("User Main Form");

                            new Alert(Alert.AlertType.CONFIRMATION, "Login Success!").show();


                        }else {
                            new Alert(Alert.AlertType.CONFIRMATION, "Incorrect Username or Password!").show();
                        }

                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }else {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
            }

        }

    }

    @FXML
    void btnSignupHereOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/both/register_form.fxml"));
        Scene scene = new Scene(rootNode);
        loginPage.getChildren().clear();
        Stage primaryStage = (Stage) loginPage.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Register Form");
    }

    @FXML
    void btnForgotPasswordOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/both/forgot_password_form.fxml"));
        Scene scene = new Scene(rootNode);
        loginPage.getChildren().clear();
        Stage primaryStage = (Stage) loginPage.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Reset Password Form");
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        passwordField.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @FXML
    void checkBoxPwOnAction(ActionEvent event) {

        if (checkBoxPw.isSelected()){
            txtPassword.setText(passwordField.getText());
            txtPassword.setVisible(true);
            passwordField.setVisible(false);
        }
        else{
            passwordField.setText(txtPassword.getText());
            passwordField.setVisible(true);
            txtPassword.setVisible(false);
        }

    }

    @FXML
    void passwordFieldOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}
