package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User user) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean checkUserCredential(String email, String password) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User";
        Query query = session.createQuery(hql);

        List<User> userList = query.list();

        for(User user : userList){
            String userEmail = user.getUserEmail();
            String userPassword = user.getUserPassword();

            if(email.equals(userEmail)){
                if(password.equals(userPassword)){
                    return true;
                }
            }
        }

        transaction.commit();
        session.close();

        return false;
    }

    @Override
    public int setCurrentNumber() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(userEmail) FROM User";
        Query query = session.createQuery(hql);

        Long count = (Long) query.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(count);      // convert a long value to an int value
    }

    @Override
    public String getName(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT userName FROM User WHERE userEmail=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);

        String name = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return name;
    }

    @Override
    public boolean updateUserDetails(String name, String newName, String newEmail, String newPw) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE User SET userName=:username,userEmail=:email,userPassword=:pw WHERE userName=:name";
        Query query = session.createQuery(hql);
        query.setParameter("username", newName);
        query.setParameter("email", newEmail);
        query.setParameter("pw", newPw);
        query.setParameter("name", name);

        int affectedRows = query.executeUpdate();

        boolean updated = affectedRows > 0;

        transaction.commit();
        session.close();

        return updated;
    }

    @Override
    public List<User> getAll() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User";
        Query query = session.createQuery(hql);

        List<User> users = query.list();

        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public boolean delete(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, email);

        session.delete(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public String getUserBranch(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT branch.id FROM User WHERE userEmail=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);

        String branchId = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return branchId;

    }

    @Override
    public User getUser(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, email);

        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public boolean checkUserName(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User";
        Query query = session.createQuery(hql);

        List<User> userList = query.list();

        for(User user : userList){
            String userEmail = user.getUserEmail();

            if(email.equals(userEmail)){
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

        String hql = "UPDATE User SET userPassword=:pw WHERE userEmail=:email";
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
    public boolean update(User entity) throws SQLException {
        return false;
    }

    @Override
    public User search(String name) throws SQLException {
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

}
