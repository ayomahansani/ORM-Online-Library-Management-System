package lk.ijse.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.controller.both.LoginFormController;

import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;


public class UserDetailsSettingFormController {

    @FXML
    private AnchorPane settingPage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPassword;

    @FXML
    private Button btnChange;

    @FXML
    private TextField txtNewEmail;

    @FXML
    private TextField txtNewPw;

    @FXML
    private TextField txtNewName;

    @FXML
    private Button btnDeleteAccount;


    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    private boolean validateUserUpdatingDetails() {

        //validate name
        String name = txtNewName.getText();
        boolean matchesName = Pattern.matches("[a-zA-Z\\s]{2,}", name);
        if(!matchesName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        //validate email
        String email = txtNewEmail.getText();
        boolean matchesEmail = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{10,}", email);
        if(!matchesEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            return false;
        }

        //validate password
        String password = txtNewPw.getText();
        boolean matchesPassword = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{8,}", password);
        if(!matchesPassword){
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            return false;
        }

        return true;
    }


    public void initialize(){
        loadUserDetails(LoginFormController.email, LoginFormController.password);
    }

    public void loadUserDetails(String email, String password){

        try{

            String name = userBO.getName(email);

            lblName.setText(name);
            lblEmail.setText(email);
            lblPassword.setText(password);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnChangeOnAction(ActionEvent event) {

        boolean isValidated = validateUserUpdatingDetails();

        if(isValidated){

            String newName = txtNewName.getText();
            String newEmail = txtNewEmail.getText();
            String newPw = txtNewPw.getText();
            String name = lblName.getText();

            if(!newName.isEmpty() && !newEmail.isEmpty() && !newPw.isEmpty()){

                try{

                    boolean isDetailsUpdated = userBO.updateUserDetails(name,newName,newEmail,newPw);

                    if(isDetailsUpdated){

                        lblName.setText(newName);
                        lblEmail.setText(newEmail);
                        lblPassword.setText(newPw);

                        new Alert(Alert.AlertType.CONFIRMATION, "Changed Successfully!").show();

                        txtNewName.setText("");
                        txtNewEmail.setText("");
                        txtNewPw.setText("");
                    }

                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    @FXML
    void btnDeleteAccountOnAction(ActionEvent event) {

        String email = lblEmail.getText();

        btnDeleteAccount.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete your account?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                try{

                    boolean isAccountDeleted = userBO.deleteAccount(email);   // Using loose coupling
                    if (isAccountDeleted) {

                        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user/user_register_form.fxml"));
                        Scene scene = new Scene(rootNode);
                        settingPage.getChildren().clear();
                        Stage primaryStage = (Stage) settingPage.getScene().getWindow();
                        primaryStage.setScene(scene);
                        primaryStage.setTitle("Register Form");

                        new Alert(Alert.AlertType.CONFIRMATION, "Your account is deleted!").show();
                    }
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }

            }
        });
    }

    @FXML
    void txtNewNameOnAction(ActionEvent event) {
        txtNewEmail.requestFocus();
    }

    @FXML
    void txtNewEmailOnAction(ActionEvent event) {
        txtNewPw.requestFocus();
    }

    @FXML
    void txtNewPwOnAction(ActionEvent event) {
        btnChangeOnAction(event);
    }

}
