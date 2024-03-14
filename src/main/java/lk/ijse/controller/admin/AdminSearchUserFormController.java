package lk.ijse.controller.admin;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.QueryBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.tm.UserHistoryTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminSearchUserFormController {

    @FXML
    private AnchorPane userHistoryForm;

    @FXML
    private TableView<UserHistoryTm> tblUserBookDetails;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturnOrNot;

    @FXML
    private JFXComboBox<String> cmbUsers;

    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);


    public void initialize(){
        loadAllUsers();
    }

    private void loadAllUsers() {

        try{

            ObservableList<String> obList = FXCollections.observableArrayList();

            List<UserDTO> userDTOS = userBO.getAllUsers();

            for(UserDTO dto : userDTOS){
                obList.add(dto.getUser_name());
            }

            cmbUsers.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbUsersOnAction(ActionEvent event) {

        String userName = cmbUsers.getValue();

        loadUserTransactionHistory(userName);
        setCellValueFactory();
    }

    private void loadUserTransactionHistory(String userName) {

        ObservableList<UserHistoryTm> obList = FXCollections.observableArrayList();

        try{

            List<UsersBorrowingBooksDTO> usersHistory = queryBO.getUserHistory(userName);

            for(UsersBorrowingBooksDTO historyDto : usersHistory){

                BookDTO bookDTO = historyDto.getBookDTO();
                String bookTitle = bookDTO.getBook_title();

                boolean isReturn = historyDto.is_return();

                if(isReturn == true){
                    obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(), "Returned"));
                }else {
                    obList.add(new UserHistoryTm(bookTitle, historyDto.getBorrow_date(),historyDto.getDue_date(),historyDto.getReturn_date(), "Not Returned"));
                }

            }

            tblUserBookDetails.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {

        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnOrNot.setCellValueFactory(new PropertyValueFactory<>("returnOrNot"));
    }

    @FXML
    void btnViewMoreOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/admin/users_notReturned_within_duedate_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Warning Users");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
