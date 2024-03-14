package lk.ijse.controller;

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
import lk.ijse.bo.custom.BranchBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.BranchBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserRegisterFormController {

    @FXML
    private AnchorPane userSignUpPage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private JFXComboBox<String> cmbBranchAddress;

    @FXML
    private Button btnLoginHere;

    private BranchBO branchBO = (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BRANCH);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    public void initialize(){
        loadBranchAddress();
    }

    private void loadBranchAddress() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{

            List<BranchDTO> branchDTOS = branchBO.getAllBranches();

            for(BranchDTO dto : branchDTOS){
                obList.add(dto.getBranch_address());
            }

            cmbBranchAddress.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        String userName = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String branchAddress = cmbBranchAddress.getValue();

        if(!userName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !branchAddress.isEmpty()){

            try{

                BranchDTO branchDTO = branchBO.getBranchByAddress(branchAddress);

                UserDTO userDTO = new UserDTO(email,userName,password,branchDTO);

                boolean isUserSignUp = userBO.saveUserSignUp(userDTO);

                if(isUserSignUp){

                    Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
                    Scene scene = new Scene(rootNode);
                    userSignUpPage.getChildren().clear();
                    Stage primaryStage = (Stage) userSignUpPage.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Login Form");

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
        userSignUpPage.getChildren().clear();
        Stage primaryStage = (Stage) userSignUpPage.getScene().getWindow();
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

}
