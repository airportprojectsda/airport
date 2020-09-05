package com.airport;

import com.airport.service.BookingService;
import com.airport.service.SearchService;
import java.time.Instant;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Application {

    public static void main(String[] args) {

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
            .buildMetadata()
            .buildSessionFactory();

//        BookingService bookingService = new BookingService(new SearchService());
//        bookingService.bookFlight("ACD123456", "Warszawa", "Moskwa", Instant.parse("2020-08-19T16:30:00.Z"), 2);

    }

}