package lk.ijse.controller.admin;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AdminMainFormController {

    @FXML
    private AnchorPane admin_main_form;

    @FXML
    private Label lblTodayDate;

    @FXML
    private Label lblTodayDate1;

    @FXML
    private Label lblCurrentTime;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnBranches;

    @FXML
    private Button btnBook;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane admin_dashboard;

    public void initialize() throws IOException {
        setDate();
        setTime();
        setDashBoardForm();
    }

    private void setDashBoardForm() throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/admin/admin_dashboard_form.fxml"));

        this.admin_dashboard.getChildren().clear();
        this.admin_dashboard.getChildren().add(form);
    }

    private void setDate() {
        //LocalDate now = LocalDate.now();
        lblTodayDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setTime() {

        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(true) {
                try{
                    Thread.sleep(500);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    lblCurrentTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    @FXML
    void btnBookOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/admin/book_manage_form.fxml"));

        this.admin_dashboard.getChildren().clear();
        this.admin_dashboard.getChildren().add(form);
    }

    @FXML
    void btnBranchesOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/admin/branch_manage_form.fxml"));

        this.admin_dashboard.getChildren().clear();
        this.admin_dashboard.getChildren().add(form);
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/admin/admin_dashboard_form.fxml"));

        this.admin_dashboard.getChildren().clear();
        this.admin_dashboard.getChildren().add(form);
    }

    @FXML
    void btnUsersOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/admin/admin_search_user_form.fxml"));

        this.admin_dashboard.getChildren().clear();
        this.admin_dashboard.getChildren().add(form);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/both/login_form.fxml"));
        Scene scene = new Scene(rootNode);
        admin_dashboard.getChildren().clear();
        Stage primaryStage = (Stage) admin_dashboard.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Login Form");
    }

}
