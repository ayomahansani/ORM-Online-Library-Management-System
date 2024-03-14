package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UsersBorrowingBooksDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsersBorrowingBooksDAOImpl implements UsersBorrowingBooksDAO {

    @Override
    public int setCurrentNumber(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(transactionId) FROM Users_Borrowing_Books WHERE user.userEmail=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email",email);

        Long count = (Long) query.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(count);  // convert a long value to an int value
    }

    @Override
    public int setReturnedCurrentNumber(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(transactionId) FROM Users_Borrowing_Books WHERE user.userEmail=:email and isReturn=:isReturn";
        Query query = session.createQuery(hql);
        query.setParameter("email",email);
        query.setParameter("isReturn",true);

        Long count = (Long) query.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(count);  // convert a long value to an int value
    }

    @Override
    public int setPendingCurrentNumber(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(transactionId) FROM Users_Borrowing_Books WHERE user.userEmail=:email and isReturn=:isReturn";
        Query query = session.createQuery(hql);
        query.setParameter("email",email);
        query.setParameter("isReturn",false);

        Long count = (Long) query.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(count);  // convert a long value to an int value
    }
}

