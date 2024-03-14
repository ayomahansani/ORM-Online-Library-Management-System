package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.AdminDAO;
import lk.ijse.entity.Admin;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean checkAdminCredential(String email, String password, String password1) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Admin";
        Query query = session.createQuery(hql);

        List<Admin> adminList = query.list();

        for(Admin admin : adminList){
            String adminEmail = admin.getAdminEmail();
            String adminPassword = admin.getAdminPassword();

            if(adminEmail.equals(email)){
                if(adminPassword.equals(password) || adminPassword.equals(password1)){
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
    public boolean checkUserName(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Admin";
        Query query = session.createQuery(hql);

        List<Admin> userList = query.list();

        for(Admin admin : userList){
            String adminEmail = admin.getAdminEmail();

            if(email.equals(adminEmail)){
                return true;
            }
        }

        transaction.commit();
        session.close();

        return false;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Admin SET adminPassword=:pw WHERE adminEmail=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("pw", newPassword);

        int affectedRows = query.executeUpdate();

        boolean updated = affectedRows > 0;

        transaction.commit();
        session.close();

        return updated;
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
