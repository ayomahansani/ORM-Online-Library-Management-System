package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UsersBorrowingBooksBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UsersBorrowingBooksDAO;
import lk.ijse.dao.custom.impl.UsersBorrowingBooksDAOImpl;
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
import java.util.Date;
import java.util.List;

public class UsersBorrowingBooksBOImpl implements UsersBorrowingBooksBO {

    private UsersBorrowingBooksDAO usersBorrowingBooksDAO = (UsersBorrowingBooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USERS_BORROWING_BOOKS);

    @Override
    public int setCurrentNumberOfAllBorrowedBooks(String email) throws SQLException {
        return usersBorrowingBooksDAO.setCurrentNumber(email);
    }

    @Override
    public int setCurrentNumberOfReturnedBooks(String email) throws SQLException {
        return usersBorrowingBooksDAO.setReturnedCurrentNumber(email);
    }

    @Override
    public int setCurrentNumberOfHaveToReturnBooks(String email) throws SQLException {
        return usersBorrowingBooksDAO.setPendingCurrentNumber(email);
    }
}
