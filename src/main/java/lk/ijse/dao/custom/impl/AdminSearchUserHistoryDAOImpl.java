package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.entity.Users_Borrowing_Books;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdminSearchUserHistoryDAOImpl {
    public List<Users_Borrowing_Books> getAll() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Users_Borrowing_Books";
        Query query = session.createQuery(hql);

        List<Users_Borrowing_Books> usersBorrowingBooks = query.list();

        transaction.commit();
        session.close();

        return usersBorrowingBooks;
    }
}
