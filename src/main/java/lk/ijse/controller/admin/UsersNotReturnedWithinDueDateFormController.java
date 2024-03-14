package lk.ijse.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.QueryBO;
import lk.ijse.bo.custom.impl.QueryBOImpl;
import lk.ijse.dto.UserDTO;
import lk.ijse.tm.UserTm;

import java.sql.SQLException;
import java.util.List;

public class UsersNotReturnedWithinDueDateFormController {

    @FXML
    private AnchorPane popUpPage;

    @FXML
    private TableView<UserTm> tblUsers;

    @FXML
    private TableColumn<?, ?> colUsername;

    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);


    public void initialize() {
        setCellValueFactory();
        loadUsersNotReturnedYet();
    }

    private void loadUsersNotReturnedYet() {

        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        try{

            List<UserDTO> allUsers = queryBO.loadUsersNotReturnedYet();

            for(UserDTO dto : allUsers){
                obList.add(new UserTm(dto.getUser_email(), dto.getUser_name(), dto.getUser_password()));
            }

            tblUsers.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("user_name"));
    }

}
