package com.airport.service;

import static org.assertj.core.groups.Tuple.tuple;

import com.airport.model.Flight;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class SearchServiceTest {

    private static SearchService searchService;
    private static Session session;

    @BeforeAll
    static void init() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
            .buildMetadata()
            .buildSessionFactory();
        session = sessionFactory.openSession();
        searchService = new SearchService(session);
    }

    @AfterAll
    static void end() {
        session.close();
    }

    @Test
    void shouldFindFlightsWhenCriteriaNotOutOfRange() {
        //given
        String fromCity = "Warszawa";
        String toCity = "Moskwa";
        Instant departureTime = Instant.parse("2020-08-19T16:30:00Z");
        Integer seatsNumber = 4;

        //when
        List<Flight> actualFlights = searchService.searchFlightsByDynamicCriteria(fromCity, toCity, departureTime, seatsNumber);

        //then
        Assertions.assertThat(actualFlights)
            .extracting(
                Flight::getFlightId,
                Flight::getDepartureTime,
                Flight::getArrivalTime,
                Flight::getPrice,
                Flight::getToCity)
            .containsExactlyInAnyOrder(
                tuple(2L, Instant.parse("2020-08-19T17:30:00Z"), Instant.parse("2020-08-19T20:30:00Z"), 199.00, "Moskwa"),
                tuple(4L, Instant.parse("2020-08-19T16:30:00Z"), Instant.parse("2020-08-19T18:30:00Z"), 399.00, "Moskwa")
            );
    }

    @Test
    void shouldNotFindFlightsWhenCriteriaOutOfRange() {
        //given
        String fromCity = null;
        String toCity = null;
        Instant departureTime = null;
        Integer seatsNumber = 4000;

        //when
        List<Flight> actualFlights = searchService.searchFlightsByDynamicCriteria(fromCity, toCity, departureTime, seatsNumber);

        //then
        Assertions.assertThat(actualFlights).hasSize(0);
    }

}