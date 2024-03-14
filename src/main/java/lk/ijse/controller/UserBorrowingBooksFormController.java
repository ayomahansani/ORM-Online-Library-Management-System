package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BookBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.BookBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.tm.BookTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserBorrowingBooksFormController {

    @FXML
    private AnchorPane userBorrowingBooksPage;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnNone;

    @FXML
    private JFXComboBox<String> cmbSelectBookGenre;

    @FXML
    private TableView<BookTm> tblBooks;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colAvailabilityStatus;

    private BookBO bookBO = (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    private UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    static BookDTO bookDTO;

    public void initialize() {
        setCellValueFactory();
        loadAllBooks();
        tableListener();
        setBookGenresToCombobox();
    }

    private void tableListener() {
        tblBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(BookTm newValue) {
        txtSearchBar.setText(newValue.getTitle());
    }

    private void loadAllBooks() {

        ObservableList<BookTm> obList = FXCollections.observableArrayList();

        try{

            String branchId = userBO.getUserBranch(LoginFormController.email);

            List<BookDTO> allBooks = bookBO.getBooksSpecificByBranch(branchId);

            for(BookDTO dto : allBooks){
                BranchDTO branchDTO = dto.getBranchDTO();
                boolean availabilityStatus = dto.isAvailability_status();

                if(availabilityStatus == true){
                    obList.add(new BookTm(dto.getBook_id(), dto.getBook_title(), dto.getBook_author(), dto.getBook_genre(), branchDTO.getBranch_address(), "Available"));
                }else{
                    obList.add(new BookTm(dto.getBook_id(), dto.getBook_title(), dto.getBook_author(), dto.getBook_genre(), branchDTO.getBranch_address(), "Not Available"));
                }
            }

            tblBooks.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAvailabilityStatus.setCellValueFactory(new PropertyValueFactory<>("availability_status"));
    }

    private void setBookGenresToCombobox() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{

            String branchId = userBO.getUserBranch(LoginFormController.email);

            List<String> bookGenres = bookBO.getBookGenresSpecificByBranch(branchId);

            obList.addAll(bookGenres);

            cmbSelectBookGenre.setItems(obList);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        try{

            String bookTitle = txtSearchBar.getText();

            String branchId = userBO.getUserBranch(LoginFormController.email);

            bookDTO = bookBO.isBookAvailable(bookTitle,branchId);

            try {

                if(bookDTO != null){

                    Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/book_view_form.fxml"));
                    Scene scene = new Scene(anchorPane);

                    Stage stage = new Stage();
                    stage.setTitle("Book");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                }else {
                    new Alert(Alert.AlertType.INFORMATION, "This Book is not available in this branch").show();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSelectBookGenreOnActions(ActionEvent event) {

        try{

            String bookGenre = cmbSelectBookGenre.getValue();

            String branchId = userBO.getUserBranch(LoginFormController.email);

            List<BookDTO> allBooks = bookBO.getBooksSpecificByGenre(bookGenre,branchId);

            ObservableList<BookTm> obList = FXCollections.observableArrayList();

            for(BookDTO dto : allBooks){
                BranchDTO branchDTO = dto.getBranchDTO();
                boolean availabilityStatus = dto.isAvailability_status();

                if(availabilityStatus == true){
                    obList.add(new BookTm(dto.getBook_id(), dto.getBook_title(), dto.getBook_author(), dto.getBook_genre(), branchDTO.getBranch_address(), "Available"));
                }else{
                    obList.add(new BookTm(dto.getBook_id(), dto.getBook_title(), dto.getBook_author(), dto.getBook_genre(), branchDTO.getBranch_address(), "Not Available"));
                }
            }

            tblBooks.setItems(obList);

            setCellValueFactory();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnNoneOnAction(ActionEvent event) {

        cmbSelectBookGenre.setValue("");

        initialize();
    }

    @FXML
    void txtSearchBarOnAction(ActionEvent event) {
        btnSearchOnAction(event);
    }

}
