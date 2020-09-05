package com.airport;

import com.airport.service.BookingService;
import com.airport.service.CreateService;
import com.airport.service.DeleteService;
import com.airport.service.SearchService;
import java.time.Instant;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Application {

    public static void main(String[] args) {

        SearchService searchService = new SearchService();
        BookingService bookingService = new BookingService(searchService, new CreateService());
        System.out.println("Flight ticket booking:");
        System.out.println("Result: " + bookingService.bookFlight("ABC123456", "Warszawa", "Moskwa", Instant.parse("2020-08-19T16:30:00.Z"), 2));
        DeleteService deleteService = new DeleteService(searchService);
        System.out.println("Flight ticket cancel:");
        System.out.println("Result: " + deleteService.cancelFlight("cd1c4044-6ea5-4d0b-96f0-d958f635e4ce"));

    }

}