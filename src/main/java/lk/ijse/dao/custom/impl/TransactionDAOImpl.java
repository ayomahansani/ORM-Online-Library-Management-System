package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TransactionDAOImpl {

    public String generateNextId() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT transactionId FROM Users_Borrowing_Books ORDER BY transactionId DESC LIMIT 1";
        Query query = session.createQuery(hql);

        String id = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return splitId(id);
    }

    private String splitId(String currentTransactionId) {

        if(currentTransactionId != null) {
            String[] split = currentTransactionId.split("00");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "000" + id;
            }else {
                return "00" + id;
            }
        } else {
            return "0001";
        }
    }

}
