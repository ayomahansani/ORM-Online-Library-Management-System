package lk.ijse.controller.admin;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BookBO;
import lk.ijse.bo.custom.BranchBO;
import lk.ijse.bo.custom.impl.BookBOImpl;
import lk.ijse.bo.custom.impl.BranchBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.tm.BookTm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BookManageFormController {

    @FXML
    private AnchorPane bookManageForm;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtGenre;

    @FXML
    private TextField txtBookSearch;

    @FXML
    private TableView<BookTm> tblBooks;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colBranch;

    @FXML
    private TableColumn<?, ?> colAvailibilityStatus;

    @FXML
    private JFXComboBox<String> cmbBranchAddress;

    @FXML
    private JFXComboBox<String> cmbAvailabilityStatus;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnClear;

    @FXML
    private TextField txtAuthor;


    private BookBO bookBO = (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    private BranchBO branchBO = (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BRANCH);


    private boolean validateBookInfo(){

        //validate id
        String id = txtBookId.getText();
        boolean matchesId = Pattern.matches("[B][0-9]{3,}", id);
        if(!matchesId){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return false;
        }

        //validate title
        String title = txtTitle.getText();
        boolean matchesTitle = Pattern.matches("[A-Za-z0-9\\s]{1,}[^!@%* .]", title);
        if(!matchesTitle){
            new Alert(Alert.AlertType.ERROR, "Invalid Title").show();
            return false;
        }

        //validate authorName
        String authorName = txtAuthor.getText();
        boolean matchesAuthorName = Pattern.matches("[A-Za-z\\s]{2,}[^!@%* .]", authorName);
        if(!matchesAuthorName){
            new Alert(Alert.AlertType.ERROR, "Invalid Author Name").show();
            return false;
        }

        //validate genre
        String genre = txtGenre.getText();
        boolean matchesGenre = Pattern.matches("[A-Za-z\\s]{2,}[^!@%* .]", genre);
        if(!matchesGenre){
            new Alert(Alert.AlertType.ERROR, "Invalid Genre").show();
            return false;
        }

        return true;
    }


    public void initialize(){
        setCellValueFactory();
        loadAllBooks();
        tableListener();
        generateNextBookId();
        loadBranchAddress();
        setValuesToAvailabilityComboBox();
    }

    private void generateNextBookId() {
        try{

            String bookId = bookBO.generateNextBookId();    // Using loose coupling
            txtBookId.setText(bookId);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableListener() {
        tblBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(BookTm newValue) {
        txtBookId.setText(newValue.getBook_id());
        txtTitle.setText(newValue.getTitle());
        txtAuthor.setText(newValue.getAuthor());
        txtGenre.setText(newValue.getGenre());
        cmbBranchAddress.setValue(newValue.getBranchAddress());
        cmbAvailabilityStatus.setValue(newValue.getAvailability_status());
    }

    private void setCellValueFactory() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branchAddress"));
        colAvailibilityStatus.setCellValueFactory(new PropertyValueFactory<>("availability_status"));
    }

    private void loadAllBooks() {

        ObservableList<BookTm> obList = FXCollections.observableArrayList();

        try{
            List<BookDTO> allBooks = bookBO.getAllBooks();

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

    private void setValuesToAvailabilityComboBox() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Available");
        obList.add("Not Available");

        cmbAvailabilityStatus.setItems(obList);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        boolean isValidated = validateBookInfo();

        if(isValidated){

            String bookId = txtBookId.getText();
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String genre = txtGenre.getText();
            String branchAddress = cmbBranchAddress.getValue();
            String availabilityStatus = cmbAvailabilityStatus.getValue();

            try{

                if(!bookId.isEmpty() && !title.isEmpty() && !author.isEmpty() && !genre.isEmpty() && !branchAddress.isEmpty() && !availabilityStatus.isEmpty()){

                    BranchDTO branchDTO = branchBO.getBranchByAddress(branchAddress);

                    if(availabilityStatus.equals("Available")){
                        BookDTO bookDTO = new BookDTO(bookId,title,author,genre,true,branchDTO);

                        boolean isBookSaved = bookBO.saveBook(bookDTO);

                        if(isBookSaved){
                            new Alert(Alert.AlertType.CONFIRMATION, "new book added!").show();
                            clearFields();
                            initialize();
                        }
                    }else {
                        BookDTO bookDTO = new BookDTO(bookId,title,author,genre,false,branchDTO);

                        boolean isBookSaved = bookBO.saveBook(bookDTO);

                        if(isBookSaved){
                            new Alert(Alert.AlertType.CONFIRMATION, "new book added!").show();
                            clearFields();
                            initialize();
                        }
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
                }

            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void clearFields() {
        txtBookId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtGenre.setText("");
        txtBookSearch.setText("");
        cmbBranchAddress.setValue("");
        cmbAvailabilityStatus.setValue("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        boolean isValidated = validateBookInfo();

        if(isValidated){

            String bookId = txtBookId.getText();
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String genre = txtGenre.getText();
            String branchAddress = cmbBranchAddress.getValue();
            String availabilityStatus = cmbAvailabilityStatus.getValue();

            try{

                if(!bookId.isEmpty() && !title.isEmpty() && !author.isEmpty() && !genre.isEmpty() && !branchAddress.isEmpty() && !availabilityStatus.isEmpty()){

                    BranchDTO branchDTO = branchBO.getBranchByAddress(branchAddress);

                    if(availabilityStatus.equals("Available")){
                        BookDTO bookDTO = new BookDTO(bookId,title,author,genre,true,branchDTO);

                        boolean isBookUpdated = bookBO.updateBook(bookDTO);

                        if(isBookUpdated){
                            new Alert(Alert.AlertType.CONFIRMATION, "book updated!").show();
                            clearFields();
                            initialize();
                        }
                    }else {
                        BookDTO bookDTO = new BookDTO(bookId,title,author,genre,false,branchDTO);

                        boolean isBookUpdated = bookBO.updateBook(bookDTO);

                        if(isBookUpdated){
                            new Alert(Alert.AlertType.CONFIRMATION, "book updated!").show();
                            clearFields();
                            initialize();
                        }
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
                }

            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                if ((Pattern.matches("[B][0-9]{3,}", txtBookId.getText()))) {

                    try{

                        String id = txtBookId.getText();

                        boolean isBookDeleted = bookBO.deleteBook(id);   // Using loose coupling

                        if (isBookDeleted) {
                            new Alert(Alert.AlertType.CONFIRMATION, "book deleted!").show();
                            clearFields();
                            initialize();
                        }

                    }catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Id!").show();
                }
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void txtBookSearchOnAction(ActionEvent event) {

        if ((Pattern.matches("[A-Za-z0-9\\s]{1,}[^!@%* .]", txtBookSearch.getText()))) {

            try{

                String bookTitle = txtBookSearch.getText();

                BookDTO bookDTO = bookBO.searchBook(bookTitle);

                if(bookDTO != null){

                    BranchDTO branchDTO = bookDTO.getBranchDTO();

                    txtBookId.setText(bookDTO.getBook_id());
                    txtTitle.setText(bookDTO.getBook_title());
                    txtAuthor.setText(bookDTO.getBook_author());
                    txtGenre.setText(bookDTO.getBook_genre());
                    cmbBranchAddress.setValue(branchDTO.getBranch_address());

                    if(bookDTO.isAvailability_status() == true){
                        cmbAvailabilityStatus.setValue("Available");
                    }else {
                        cmbAvailabilityStatus.setValue("Not Available");
                    }

                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Book not found").show();
                }

            }catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Book Title").show();
        }
    }

    @FXML
    void txtBookIdOnAction(ActionEvent event){
        txtTitle.requestFocus();
    }

    @FXML
    void txtTitleOnAction(ActionEvent event) {
        txtAuthor.requestFocus();
    }

    @FXML
    void txtAuthorOnAction(ActionEvent event) {
        txtGenre.requestFocus();
    }

}
