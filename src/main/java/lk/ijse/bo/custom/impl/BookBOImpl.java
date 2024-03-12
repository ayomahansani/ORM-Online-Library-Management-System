package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.BookDAOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;

import java.util.ArrayList;
import java.util.List;

public class BookBOImpl {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    public int setCurrentNumberOfBooks() {
        return bookDAO.setCurrentNumber();
    }

    public boolean saveBook(BookDTO bookDTO) {
        BranchDTO dto = bookDTO.getBranchDTO();
        Branch branch = new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null);

        return bookDAO.save(new Book(
                bookDTO.getBook_id(),bookDTO.getBook_title(), bookDTO.getBook_author(), bookDTO.getBook_genre(), bookDTO.isAvailability_status(),branch,null));

    }

    public boolean updateBook(BookDTO bookDTO) {
        BranchDTO dto = bookDTO.getBranchDTO();
        Branch branch = new Branch(dto.getBranch_id(),dto.getBranch_address(),dto.getBranch_telephone(),null,null);

        return bookDAO.update(new Book(
                bookDTO.getBook_id(),bookDTO.getBook_title(), bookDTO.getBook_author(), bookDTO.getBook_genre(), bookDTO.isAvailability_status(),branch,null));
    }

    public boolean deleteBook(String id) {
        return bookDAO.delete(id);
    }

    public BookDTO searchBook(String bookTitle) {
        Book book = bookDAO.search(bookTitle);
        Branch branch = book.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());

        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(),book.isAvailabilityStatus(),branchDTO);
    }

    public String generateNextBookId() {
        return bookDAO.generateNextId();
    }

    public List<BookDTO> getAllBooks() {

        List<Book> books = bookDAO.getAll();
        List<BookDTO> bookDTOs = new ArrayList<>();

        for(Book dto : books){
            Branch branch = dto.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            bookDTOs.add(new BookDTO(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),dto.isAvailabilityStatus(),branchDTO));
        }
        return bookDTOs;
    }

    public List<BookDTO> getBooksSpecificByBranch(String branchId) {

        List<Book> books = bookDAO.getBooksSpecificByBranch(branchId);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for(Book dto : books){
            Branch branch = dto.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            bookDTOs.add(new BookDTO(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),dto.isAvailabilityStatus(),branchDTO));
        }
        return bookDTOs;

    }

    public BookDTO isBookAvailable(String bookTitle, String branchId) {
        Book book = bookDAO.isBookAvailable(bookTitle, branchId);
        Branch branch = book.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getBranchId(),branch.getBranchAddress(),branch.getBranchTelephone());
        return new BookDTO(book.getBookId(),book.getTitle(),book.getAuthor(),book.getGenre(),book.isAvailabilityStatus(),branchDTO);
    }

    public List<BookDTO> getBooksSpecificByGenre(String bookGenre, String branchId) {

        List<Book> books = bookDAO.getBooksSpecificByGenre(bookGenre,branchId);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for(Book dto : books){
            Branch branch = dto.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            bookDTOs.add(new BookDTO(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),dto.isAvailabilityStatus(),branchDTO));
        }
        return bookDTOs;
    }

    public List<String> getBookGenresSpecificByBranch(String branchId) {
        return bookDAO.getBookGenresSpecificByBranch(branchId);
    }
}
