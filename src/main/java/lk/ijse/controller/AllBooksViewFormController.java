package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.BookBOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.tm.BookTm;

import java.util.List;

public class AllBooksViewFormController {

    @FXML
    private AnchorPane allBooksViewPage;

    @FXML
    private TableView<BookTm> tblAllBooks;

    @FXML
    private TableColumn<?, ?> colBookName;

    private BookBOImpl bookBO = new BookBOImpl();

    public void initialize() {
        loadAllBooks();
        setCellValueFactory();
    }

    private void loadAllBooks() {
        ObservableList<BookTm> obList = FXCollections.observableArrayList();

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

        tblAllBooks.setItems(obList);
    }

    private void setCellValueFactory() {
        colBookName.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

}