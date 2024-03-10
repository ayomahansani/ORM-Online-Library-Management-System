package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.entity.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdminDAOImpl {
    public boolean checkAdminCredentia(String email, String password) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Admin";
        Query query = session.createQuery(hql);

        List<Admin> adminList = query.list();

        for(Admin admin : adminList){
            String adminEmail = admin.getAdminEmail();
            String adminPassword = admin.getAdminPassword();

            if(email.equals(adminEmail)){
                if(password.equals(adminPassword)){
                    return true;
                }
            }
        }

        transaction.commit();
        session.close();

        return false;
    }

    public boolean save(Admin admin) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(admin);

        transaction.commit();
        session.close();

        return true;
    }
}
