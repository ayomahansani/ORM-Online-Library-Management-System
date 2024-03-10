package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BookDAOImpl {
    public int setCurrentNumber() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(bookId) FROM Book";
        Query query = session.createQuery(hql);

        Long count = (Long) query.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(count);      // convert a long value to an int value
    }

    public boolean save(Book book) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(book);

        transaction.commit();
        session.close();

        return true;
    }

    public boolean update(Book book) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(book);

        transaction.commit();
        session.close();

        return true;
    }

    public boolean delete(String id) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        Book book = session.get(Book.class, id);

        session.delete(book);

        transaction.commit();
        session.close();

        return true;
    }

    public Book search(String bookTitle) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Book WHERE title=:title";
        Query query = session.createQuery(hql);
        query.setParameter("title", bookTitle);

        Book book = (Book) query.uniqueResult();

        transaction.commit();
        session.close();

        return book;
    }

    public String generateNextId() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT bookId FROM Book ORDER BY bookId DESC LIMIT 1";
        Query query = session.createQuery(hql);

        String id = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return splitId(id);
    }

    private String splitId(String currentBookId) {

        if(currentBookId != null) {
            String[] split = currentBookId.split("B0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "B00" + id;
            }else {
                return "B0" + id;
            }
        } else {
            return "B001";
        }
    }

    public List<Book> getAll() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Book";
        Query query = session.createQuery(hql);

        List<Book> books = query.list();

        transaction.commit();
        session.close();

        return books;
    }
}
