package lk.ijse.controller;

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
import lk.ijse.bo.custom.impl.AdminBOImpl;
import lk.ijse.dto.AdminDTO;

import java.io.IOException;

public class AdminRegisterFormController {

    @FXML
    private AnchorPane adminSignUpPage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnLoginHere;

    private AdminBO adminBO = (AdminBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ADMIN);


    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        String adminName = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if(!adminName.isEmpty() && !email.isEmpty() && !password.isEmpty()){

            try{
                AdminDTO adminDTO = new AdminDTO(email, adminName, password);

                boolean isAdminSignUp = adminBO.saveAdminSignUp(adminDTO);

                if(isAdminSignUp){

                    Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/admin_main_form.fxml"));
                    Scene scene = new Scene(rootNode);
                    adminSignUpPage.getChildren().clear();
                    Stage primaryStage = (Stage) adminSignUpPage.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Admin Main Form");

                    new Alert(Alert.AlertType.CONFIRMATION, "Signup Success!").show();
                }

            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else {
            new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
        }
    }

    @FXML
    void btnLoginHereOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(rootNode);
        adminSignUpPage.getChildren().clear();
        Stage primaryStage = (Stage) adminSignUpPage.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnRegisterOnAction(event);
    }

}
