package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BookDTO;

import java.sql.SQLException;

import java.util.List;

public interface BookBO extends SuperBO {

    int setCurrentNumberOfBooks() throws SQLException;
    boolean saveBook(BookDTO bookDTO) throws SQLException;
    boolean updateBook(BookDTO bookDTO) throws SQLException;
    boolean deleteBook(String id) throws SQLException;
    BookDTO searchBook(String bookTitle) throws SQLException;
    String generateNextBookId() throws SQLException;
    List<BookDTO> getAllBooks() throws SQLException;
    List<BookDTO> getBooksSpecificByBranch(String branchId) throws SQLException;
    BookDTO isBookAvailable(String bookTitle, String branchId) throws SQLException;
    List<BookDTO> getBooksSpecificByGenre(String bookGenre, String branchId) throws SQLException;
    List<String> getBookGenresSpecificByBranch(String branchId) throws SQLException;
}
