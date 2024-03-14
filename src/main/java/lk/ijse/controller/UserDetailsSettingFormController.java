package lk.ijse.controller;

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
import lk.ijse.bo.custom.impl.UserBOImpl;

import java.sql.SQLException;
import java.util.Optional;


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

    @FXML
    void btnDeleteAccountOnAction(ActionEvent event) {

        String email = lblEmail.getText();

        btnDeleteAccount.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove your account?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                try{

                    boolean isAccountDeleted = userBO.deleteAccount(email);   // Using loose coupling
                    if (isAccountDeleted) {

                        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user_register_form.fxml"));
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
