package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl {
    public boolean save(User user) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(user);

        transaction.commit();
        session.close();

        return true;
    }

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

    public boolean updateUserDetails(String name, String newName, String newEmail, String newPw) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE User SET userName = :username, userEmail = :email, userPassword = :pw WHERE userName = :name";
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

    public boolean delete(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, email);

        session.delete(user);

        transaction.commit();
        session.close();

        return true;
    }

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

    public User getUser(String email) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, email);

        transaction.commit();
        session.close();

        return user;
    }
}
