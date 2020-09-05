package com.airport.service;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Getter
public class SessionSingleton {

    private final Session session;

    private SessionSingleton() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
            .buildMetadata()
            .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    private static class SessionSingletonHelper {
        private static final SessionSingleton SESSION_SINGLETON = new SessionSingleton();
    }

    public static SessionSingleton getSessionSingleton() {
        return SessionSingletonHelper.SESSION_SINGLETON;
    }

}