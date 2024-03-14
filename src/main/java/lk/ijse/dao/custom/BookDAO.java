package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO extends CrudDAO<Book> {

    /*boolean save(Book book);
    boolean update(Book book);
    boolean delete(String id);
    Book search(String bookTitle);
    List<Book> getAll();
    String generateNextId();
    String splitId(String currentBookId);
    int setCurrentNumber();*/

    List<Book> getBooksSpecificByBranch(String branchId) throws SQLException;
    Book isBookAvailable(String bookTitle, String branchId) throws SQLException;
    List<Book> getBooksSpecificByGenre(String bookGenre, String branchId) throws SQLException;
    List<String> getBookGenresSpecificByBranch(String branchId) throws SQLException;
}
