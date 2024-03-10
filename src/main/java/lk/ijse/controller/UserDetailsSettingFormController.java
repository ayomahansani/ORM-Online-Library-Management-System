package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.UserDTO;

import java.util.List;

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

    private UserBOImpl userBO = new UserBOImpl();

    public void initialize(){
        loadUserDetails(LoginFormController.email, LoginFormController.password);
    }

    public void loadUserDetails(String email, String password){

        String name = userBO.getName(email);

        lblName.setText(name);
        lblEmail.setText(email);
        lblPassword.setText(password);
    }

    @FXML
    void btnChangeOnAction(ActionEvent event) {

        String newEmail = txtNewEmail.getText();
        String newPw = txtNewPw.getText();
        String name = lblName.getText();

        if(!newEmail.isEmpty() && !newPw.isEmpty()){

            boolean isDetailsUpdated = userBO.updateUserDetails(name,newEmail,newPw);

            if(isDetailsUpdated){

                lblEmail.setText(newEmail);
                lblPassword.setText(newPw);

                new Alert(Alert.AlertType.CONFIRMATION, "Changed Successfully!").show();

                txtNewEmail.setText("");
                txtNewPw.setText("");
            }
        }

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
