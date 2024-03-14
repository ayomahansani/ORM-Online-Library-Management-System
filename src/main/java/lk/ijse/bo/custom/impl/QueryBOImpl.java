package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.QueryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dao.custom.impl.QueryDAOImpl;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UsersBorrowingBooksDTO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QueryBOImpl implements QueryBO {

    private QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public List<UsersBorrowingBooksDTO> getUserHistory(String userName) throws SQLException {

        List<Object[]> objectsList =  queryDAO.getUserHistory(userName);

        List<UsersBorrowingBooksDTO> usersBorrowingBooksDTOS = new ArrayList<>();

        for(Object[] objects : objectsList) {

            User user = (User) objects[5];
            Branch branch = user.getBranch();
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());
            UserDTO userDTO = new UserDTO(user.getUserEmail(), user.getUserName(), user.getUserPassword(), branchDTO);

            Book book = (Book) objects[6];
            Branch branch1 = book.getBranch();
            BranchDTO branchDTO1 = new BranchDTO(branch1.getBranchId(), branch1.getBranchAddress(), branch1.getBranchTelephone());
            BookDTO bookDTO = new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailabilityStatus(), branchDTO1);

            usersBorrowingBooksDTOS.add(
                    new UsersBorrowingBooksDTO(
                            (String) objects[0],
                            (LocalDate) objects[1],
                            (LocalDate) objects[2],
                            (LocalDate) objects[3],
                            (Boolean) objects[4],
                            userDTO,
                            bookDTO
                    )
            );

        }

        return usersBorrowingBooksDTOS;

    }

    @Override
    public List<UserDTO> loadUsersNotReturnedYet() throws SQLException {

        List<Object[]> objectsList = queryDAO.loadUsersNotReturnedYet();

        List<UserDTO> userDTOS = new ArrayList<>();

        for(Object[] object : objectsList){

            Branch branch = (Branch) object[3];
            BranchDTO branchDTO = new BranchDTO(branch.getBranchId(), branch.getBranchAddress(), branch.getBranchTelephone());

            userDTOS.add(new UserDTO(
                    (String) object[0],
                    (String) object[1],
                    (String) object[2],
                    branchDTO
            ));
        }

        return userDTOS;
    }
}
