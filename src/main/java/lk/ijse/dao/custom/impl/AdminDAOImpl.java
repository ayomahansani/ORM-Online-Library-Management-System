package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.AdminDAO;
import lk.ijse.entity.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean checkAdminCredential(String email, String password) {

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

    @Override
    public boolean save(Admin admin) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(admin);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Admin dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Admin search(String name) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        return null;
    }

    @Override
    public int setCurrentNumber() throws SQLException {
        return 0;
    }
}
