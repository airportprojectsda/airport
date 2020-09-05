package com.airport.service;

import com.airport.model.Flight;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

@RequiredArgsConstructor
public class BookingService {

    private final SearchService searchService;
    private final Session session = SessionSingleton.getSessionSingleton().getSession();

    public int bookFlight(String idCard, String fromCity, String toCity, Instant departureTime, Integer seatsNumber) {
        List<Flight> flights = searchService.searchFlightsByDynamicCriteria(fromCity, toCity, departureTime, seatsNumber);
        if (flights.size() == 0) {
            return 0;
        }

        Transaction transaction = session.beginTransaction();

        transaction.commit();

        return 1;
    }

}