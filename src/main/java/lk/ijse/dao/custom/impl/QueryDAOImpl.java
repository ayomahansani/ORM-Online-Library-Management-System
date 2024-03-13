package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class QueryDAOImpl {
    public List<Object[]> loadUsersNotReturnedYet() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT u.userEmail,u.userName,u.userPassword,u.branch FROM User u JOIN Users_Borrowing_Books ubb ON u.userEmail = ubb.user.userEmail WHERE ubb.returnDate NOT BETWEEN ubb.borrowDate and ubb.dueDate";
        Query query = session.createQuery(hql);

        List<Object[]> objects = query.list();

        transaction.commit();
        session.close();

        return objects;

    }
}
