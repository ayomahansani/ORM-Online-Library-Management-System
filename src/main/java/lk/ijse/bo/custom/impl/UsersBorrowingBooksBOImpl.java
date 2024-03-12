package lk.ijse.bo.custom.impl;

import lk.ijse.dao.custom.impl.UsersBorrowingBooksDAOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersBorrowingBooksBOImpl {

    private UsersBorrowingBooksDAOImpl usersBorrowingBooksDAO = new UsersBorrowingBooksDAOImpl();

    public List<UsersBorrowingBooksDTO> getUserHistory(String userName) {

        List<Object[]> objectsList =  usersBorrowingBooksDAO.getUserHistory(userName);

        List<UsersBorrowingBooksDTO> usersBorrowingBooksDTOS = new ArrayList<>();

        for(Object[] objects : objectsList) {

            User user = (User) objects[4];
            Branch branch = user.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            UserDTO userDTO = new UserDTO(user.getUserEmail(), user.getUserName(), user.getUserPassword(), branchDTO);

            Book book = (Book) objects[5];
            Branch branch1 = book.getBranch();
            BranchDTO branchDTO1 = new BranchDTO(branch1.getBranchId(), branch1.getBranchAddress(), branch1.getBranchTelephone());
            BookDTO bookDTO = new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailabilityStatus(), branchDTO1);

            usersBorrowingBooksDTOS.add(
                    new UsersBorrowingBooksDTO(
                            (String) objects[0],
                            (LocalDate) objects[1],
                            (LocalDate) objects[2],
                            (Boolean) objects[3],
                            userDTO,
                            bookDTO
                    )
            );

        }

        return usersBorrowingBooksDTOS;

    }
}
