package lk.ijse.controller.both;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.AdminBO;
import lk.ijse.bo.custom.UserBO;

import java.io.IOException;

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

    private AdminBO adminBO = (AdminBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ADMIN);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public static String email;
    public static String password;


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
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/admin_main_form.fxml"));
            Scene scene = new Scene(rootNode);
            loginPage.getChildren().clear();
            Stage primaryStage = (Stage) loginPage.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin Main Form");

        }else{
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user_main_form.fxml"));
            Scene scene = new Scene(rootNode);
            loginPage.getChildren().clear();
            Stage primaryStage = (Stage) loginPage.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin Main Form");
        }*/


        String adminOruser = cmbAdminOrUser.getValue();
        email = txtUsername.getText();
        password = txtPassword.getText();

        if(!adminOruser.isEmpty() && !email.isEmpty() && !password.isEmpty()){

            try{

                if(adminOruser.equals("Admin")){
                    boolean isMatched = adminBO.checkAdminCredential(email, password);

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
                    boolean isMatched = userBO.checkUserCredential(email, password);

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

    @FXML
    void btnSignupHereOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/both/register_form.fxml"));
        Scene scene = new Scene(rootNode);
        loginPage.getChildren().clear();
        Stage primaryStage = (Stage) loginPage.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register Form");
    }

    @FXML
    void btnForgotPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}