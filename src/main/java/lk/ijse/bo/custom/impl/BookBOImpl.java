package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BookBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BookDAO;
import lk.ijse.dao.custom.impl.BookDAOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    private BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);

    @Override
    public int setCurrentNumberOfBooks() throws SQLException {
        return bookDAO.setCurrentNumber();
    }

    @Override
    public boolean saveBook(BookDTO bookDTO) throws SQLException {
        BranchDTO dto = bookDTO.getBranchDTO();
        Branch branch = new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null);

        return bookDAO.save(new Book(
                bookDTO.getBook_id(),bookDTO.getBook_title(), bookDTO.getBook_author(), bookDTO.getBook_genre(), bookDTO.isAvailability_status(),branch,null));

    }

    @Override
    public boolean updateBook(BookDTO bookDTO) throws SQLException {
        BranchDTO dto = bookDTO.getBranchDTO();
        Branch branch = new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null);

        return bookDAO.update(new Book(
                bookDTO.getBook_id(),bookDTO.getBook_title(), bookDTO.getBook_author(), bookDTO.getBook_genre(), bookDTO.isAvailability_status(),branch,null));
    }

    @Override
    public boolean deleteBook(String id) throws SQLException {
        return bookDAO.delete(id);
    }

    @Override
    public BookDTO searchBook(String bookTitle) throws SQLException {
        Book book = bookDAO.search(bookTitle);
        Branch branch = book.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());

        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(),book.isAvailabilityStatus(),branchDTO);
    }

    @Override
    public String generateNextBookId() throws SQLException {
        return bookDAO.generateNextId();
    }

    @Override
    public List<BookDTO> getAllBooks() throws SQLException {

        List<Book> books = bookDAO.getAll();
        List<BookDTO> bookDTOs = new ArrayList<>();

        for(Book dto : books){
            Branch branch = dto.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            bookDTOs.add(new BookDTO(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),dto.isAvailabilityStatus(),branchDTO));
        }
        return bookDTOs;
    }

    @Override
    public List<BookDTO> getBooksSpecificByBranch(String branchId) throws SQLException {

        List<Book> books = bookDAO.getBooksSpecificByBranch(branchId);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for(Book dto : books){
            Branch branch = dto.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            bookDTOs.add(new BookDTO(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),dto.isAvailabilityStatus(),branchDTO));
        }
        return bookDTOs;

    }

    @Override
    public BookDTO isBookAvailable(String bookTitle, String branchId) throws SQLException {
        Book book = bookDAO.isBookAvailable(bookTitle, branchId);
        Branch branch = book.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getBranchId(),branch.getBranchAddress(),branch.getBranchTelephone());
        return new BookDTO(book.getBookId(),book.getTitle(),book.getAuthor(),book.getGenre(),book.isAvailabilityStatus(),branchDTO);
    }

    @Override
    public List<BookDTO> getBooksSpecificByGenre(String bookGenre, String branchId) throws SQLException {

        List<Book> books = bookDAO.getBooksSpecificByGenre(bookGenre,branchId);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for(Book dto : books){
            Branch branch = dto.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            bookDTOs.add(new BookDTO(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),dto.isAvailabilityStatus(),branchDTO));
        }
        return bookDTOs;
    }

    @Override
    public List<String> getBookGenresSpecificByBranch(String branchId) throws SQLException {
        return bookDAO.getBookGenresSpecificByBranch(branchId);
    }
}
