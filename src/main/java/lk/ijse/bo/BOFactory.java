package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {    // Using Factory Design Pattern  --> For hide the object creation part

    private static BOFactory boFactory;

    private BOFactory(){
    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public enum BOTypes{    // Enumeration --> Represents the group of contents
        ADMIN,BOOK,BRANCH,QUERY,TRANSACTION,USER,USERS_BORROWING_BOOKS
    }

    public SuperBO getBO(BOTypes boTypes){  // return type must be the most super(SuperBO)

        switch (boTypes){
            case ADMIN:
                return new AdminBOImpl();
            case BOOK:
                return new BookBOImpl();
            case BRANCH:
                return new BranchBOImpl();
            case QUERY:
                return new QueryBOImpl();
            case TRANSACTION:
                return new TransactionBOImpl();
            case USER:
                return new UserBOImpl();
            case USERS_BORROWING_BOOKS:
                return new UsersBorrowingBooksBOImpl();
            default:
                return null;
        }
    }
}