package com.airport;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Application {

    public static void main(String[] args) {
        final StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        try (SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
            .buildMetadata().buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            Transaction transaction = session.beginTransaction();// transakcje są potrzebne żeby zrobić jakiekolwiek zmiany na bazie typu: insert, update, delete

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}