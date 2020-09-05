package com.airport.service;

import static org.assertj.core.groups.Tuple.tuple;

import com.airport.model.Flight;
import com.airport.model.Passenger;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class SearchServiceTest {

    private static SearchService searchService;

    @BeforeAll
    static void init() {
        searchService = new SearchService();
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
        System.out.println();
        Assertions.assertThat(actualFlights)
            .extracting(
                Flight::getFlightId,
                Flight::getDepartureTime,
                Flight::getArrivalTime,
                Flight::getToCity)
            .containsExactlyInAnyOrder(
                tuple(2L, Instant.parse("2020-08-19T17:30:00Z"), Instant.parse("2020-08-19T20:30:00Z"), "Moskwa"),
                tuple(4L, Instant.parse("2020-08-19T16:30:00Z"), Instant.parse("2020-08-19T18:30:00Z"), "Moskwa")
            );
    }

    @Test
    void shouldNotFindFlightsWhenCriteriaOutOfRange() {
        //given
        String fromCity = "Warszawa";
        String toCity = "Moskwa";
        Instant departureTime = Instant.parse("2020-08-19T16:30:00Z");
        Integer seatsNumber = 4000;

        //when
        List<Flight> actualFlights = searchService.searchFlightsByDynamicCriteria(fromCity, toCity, departureTime, seatsNumber);

        //then
        Assertions.assertThat(actualFlights).hasSize(0);
    }

    @Test
    void shouldFindOnePassenger() {
        //given
        String idCard = "";
        //when
        Passenger actualPassenger = searchService.searchPassengerByIdCard(idCard);
        //then
        Assertions.assertThat(actualPassenger)
            .extracting(
                Passenger::getFirstName,
                Passenger::getLastName)
            .containsExactly(
                tuple("", "")
            );
    }

    @Test
    void shouldNotFindAnyPassenger() {
        //given
        String idCard = "2244342121";
        //when
        Passenger actualPassenger = searchService.searchPassengerByIdCard(idCard);
        //then
        Assertions.assertThat(actualPassenger).isNull();
    }

}