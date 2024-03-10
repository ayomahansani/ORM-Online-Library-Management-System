package lk.ijse.config;

import lk.ijse.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {     // Using Singleton Design Pattern
     private static FactoryConfiguration factoryConfiguration;
     private SessionFactory sessionFactory;

     private FactoryConfiguration(){
          Configuration configuration = new Configuration()
                  .addAnnotatedClass(Admin.class)
                  .addAnnotatedClass(User.class)
                  .addAnnotatedClass(Book.class)
                  .addAnnotatedClass(Branch.class)
                  .addAnnotatedClass(Users_Borrowing_Books.class);
          sessionFactory = configuration.buildSessionFactory();  // Build a session factory using configuration
     }

     public static FactoryConfiguration getFactoryConfiguration(){
         return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
     }

     public Session getSession(){
          return sessionFactory.openSession();    // Open a session using a session factory
     }

}
