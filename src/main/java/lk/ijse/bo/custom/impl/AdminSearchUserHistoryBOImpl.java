package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.AdminSearchUserHistoryDAOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;
import lk.ijse.entity.Users_Borrowing_Books;

import java.util.ArrayList;
import java.util.List;

public class AdminSearchUserHistoryBOImpl {

    private AdminSearchUserHistoryDAOImpl adminSearchUserHistoryDAO = new AdminSearchUserHistoryDAOImpl();

    public List<UsersBorrowingBooksDTO> getUserHistory() {

        List<Users_Borrowing_Books> usersBorrowingBooks = adminSearchUserHistoryDAO.getAll();

        List<UsersBorrowingBooksDTO> usersBorrowingBooksDTOS = new ArrayList<>();

        for(Users_Borrowing_Books history : usersBorrowingBooks){

            User user = history.getUser();
            Branch branch = user.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            UserDTO userDTO = new UserDTO(user.getUserEmail(), user.getUserName(), user.getUserPassword(), branchDTO);

            Book book = history.getBook();
            Branch branch1 = book.getBranch();
            BranchDTO branchDTO1 = new BranchDTO(branch1.getBranchId(),branch1.getBranchAddress(),branch1.getBranchTelephone());
            BookDTO bookDTO = new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailabilityStatus(), branchDTO1);

            usersBorrowingBooksDTOS.add(new UsersBorrowingBooksDTO(
                    history.getTransactionId(),
                    history.getBorrowDate(),
                    history.getReturnDate(),
                    history.isReturn(),
                    userDTO,
                    bookDTO
            ));
        }

        return usersBorrowingBooksDTOS;
    }
}
