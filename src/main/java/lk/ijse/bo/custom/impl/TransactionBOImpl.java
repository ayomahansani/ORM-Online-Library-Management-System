package lk.ijse.bo.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.impl.TransactionDAOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;
import lk.ijse.entity.Users_Borrowing_Books;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;

public class TransactionBOImpl {

    private TransactionDAOImpl transactionDAO = new TransactionDAOImpl();

    public String generateNextTransactionId() {
        return transactionDAO.generateNextId();
    }

    public boolean isBorrowBook(UsersBorrowingBooksDTO dto) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        // Insert transaction values

        UserDTO userDTO = dto.getUserDTO();
        BranchDTO branchDTO = userDTO.getBranchDTO();
        Branch branch = new Branch(branchDTO.getBranch_id(), branchDTO.getBranch_address(), branchDTO.getBranch_telephone(), null,null);
        User user = new User(userDTO.getUser_email(), userDTO.getUser_name(), userDTO.getUser_password(), branch,null);

        BookDTO bookDTO = dto.getBookDTO();
        BranchDTO branchDTO1 = bookDTO.getBranchDTO();
        Branch branch1 = new Branch(branchDTO1.getBranch_id(), branchDTO1.getBranch_address(), branchDTO1.getBranch_telephone(), null,null);
        Book book = new Book(bookDTO.getBook_id(), bookDTO.getBook_title(), bookDTO.getBook_author(), bookDTO.getBook_genre(), bookDTO.isAvailability_status(),branch1,null);


        Users_Borrowing_Books usersBorrowingBooks = new Users_Borrowing_Books(
                dto.getTransaction_id(),
                dto.getBorrow_date(),
                dto.getDue_date(),
                dto.getReturn_date(),
                dto.is_return(),
                user,
                book
        );

        session.persist(usersBorrowingBooks);


        // Update book availability

        book.setAvailabilityStatus(false);

        session.update(book);

        transaction.commit();
        session.close();

        return true;
    }

    public boolean updateIsReturn(UsersBorrowingBooksDTO dto) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        UserDTO userDTO = dto.getUserDTO();
        BranchDTO branchDTO = userDTO.getBranchDTO();
        Branch branch = new Branch(branchDTO.getBranch_id(), branchDTO.getBranch_address(), branchDTO.getBranch_telephone(), null,null);
        User user = new User(userDTO.getUser_email(), userDTO.getUser_name(), userDTO.getUser_password(), branch,null);

        BookDTO bookDTO = dto.getBookDTO();
        BranchDTO branchDTO1 = bookDTO.getBranchDTO();
        Branch branch1 = new Branch(branchDTO1.getBranch_id(), branchDTO1.getBranch_address(), branchDTO1.getBranch_telephone(), null,null);
        Book book = new Book(bookDTO.getBook_id(), bookDTO.getBook_title(), bookDTO.getBook_author(), bookDTO.getBook_genre(), bookDTO.isAvailability_status(),branch1,null);

        dto.setReturn_date(LocalDate.now());

        Users_Borrowing_Books usersBorrowingBooks = new Users_Borrowing_Books(
                dto.getTransaction_id(),
                dto.getBorrow_date(),
                dto.getDue_date(),
                dto.getReturn_date(),
                true,
                user,
                book
        );

        session.update(usersBorrowingBooks);

        book.setAvailabilityStatus(true);

        session.update(book);


        transaction.commit();
        session.close();

        return true;
    }
}
