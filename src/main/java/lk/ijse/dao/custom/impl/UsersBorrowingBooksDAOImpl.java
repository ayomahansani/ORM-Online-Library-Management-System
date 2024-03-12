package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsersBorrowingBooksDAOImpl {

    public List<Object[]> getUserHistory(String userName) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT ubb.transactionId,ubb.borrowDate,ubb.returnDate,ubb.isReturn,ubb.user,ubb.book FROM User u JOIN Users_Borrowing_Books ubb ON u.userEmail = ubb.user.userEmail WHERE u.userName = :name";
        Query query = session.createQuery(hql);
        query.setParameter("name", userName);

        List<Object[]> objects = query.list();

        transaction.commit();
        session.close();

        return objects;
    }
}

