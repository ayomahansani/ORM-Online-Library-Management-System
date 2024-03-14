package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {   // Using Factory Design Pattern  --> For hide the object creation part // And also using Singleton Design Pattern

    private static DAOFactory daoFactory;

    private DAOFactory(){
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{   // Enumeration --> Represents the group of contents
        ADMIN,BOOK,BRANCH,QUERY,TRANSACTION,USER,USERS_BORROWING_BOOKS
    }

    public SuperDAO getDAO(DAOTypes daoTypes){  // return type must be the most super(SuperDAO)

        switch (daoTypes){
            case ADMIN:
                return new AdminDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case BRANCH:
                return new BranchDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case TRANSACTION:
                return new TransactionDAOImpl();
            case USER:
                return new UserDAOImpl();
            case USERS_BORROWING_BOOKS:
                return new UsersBorrowingBooksDAOImpl();
            default:
                return null;
        }
    }

}
