package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public List<Object[]> getUserHistory(String userName) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT ubb.transactionId,ubb.borrowDate,ubb.dueDate,ubb.returnDate,ubb.isReturn,ubb.user,ubb.book FROM User u JOIN Users_Borrowing_Books ubb ON u.userEmail = ubb.user.userEmail WHERE u.userName = :name ORDER BY ubb.transactionId";
        Query query = session.createQuery(hql);
        query.setParameter("name", userName);

        List<Object[]> objects = query.list();

        transaction.commit();
        session.close();

        return objects;
    }

    @Override
    public List<Object[]> loadUsersNotReturnedYet() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT u.userEmail,u.userName,u.userPassword,u.branch FROM User u JOIN Users_Borrowing_Books ubb ON u.userEmail = ubb.user.userEmail WHERE ubb.returnDate NOT BETWEEN ubb.borrowDate and ubb.dueDate GROUP BY u.userEmail";
        Query query = session.createQuery(hql);

        List<Object[]> objects = query.list();

        transaction.commit();
        session.close();

        return objects;

    }
}
