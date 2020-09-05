package com.airport.service;

import com.airport.model.Flight;
import com.airport.model.Passenger;
import com.airport.model.Reservation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

@RequiredArgsConstructor
public class BookingService {

    private final static Pattern ID_CARD_PATTERN = Pattern.compile("[a-zA-Z]{3}[0-9]{6}");
    private final static Pattern NAME_PASSENGER_PATTERN = Pattern.compile("[a-zA-Z]{1,}");

    private final SearchService searchService;
    private final CreateService createService;
    private final Session session = SessionSingleton.getSessionSingleton().getSession();
    private final Scanner scanner = new Scanner(System.in);

    public boolean bookFlight(String idCard, String fromCity, String toCity, Instant departureTime, int seatsNumber) {
        List<Flight> flights = searchService.searchFlightsByDynamicCriteria(fromCity, toCity, departureTime, seatsNumber);

        if (flights.size() == 0) {
            return false;
        }

        Passenger passenger = searchService.searchPassengerByIdCard(idCard);
        Transaction transaction = session.beginTransaction();

        if (passenger == null) {
            passenger = createPassenger();
            createService.registerPassenger(passenger);
        }

        Optional<Flight> flight = chooseFlight(flights);
        Reservation reservation = createTicket();
        createService.addReservation(reservation);

        Passenger finalPassenger = passenger;
        flight.ifPresent(specifiedFlight -> {
            List<Reservation> reservations = finalPassenger.getReservations();
            reservations = reservations == null
                ? new ArrayList<>()
                : reservations;
            reservations.add(reservation);
            finalPassenger.setReservations(reservations);
            specifiedFlight.getPassengers().add(finalPassenger);
            int numberOfVacancies = specifiedFlight.getPlane().getNumberOfVacancies();
            specifiedFlight.getPlane().setNumberOfVacancies(numberOfVacancies - seatsNumber);
            session.update(flight.get());
        });
        transaction.commit();

        return true;
    }

    private Passenger createPassenger() {
        String firstName;
        String lastName;
        String idCard;

        do {
            System.out.println("Give your first name:");
            firstName = scanner.nextLine();
        } while (!NAME_PASSENGER_PATTERN.matcher(firstName).matches());

        do {
            System.out.println("Give your last name:");
            lastName = scanner.nextLine();
        } while (!NAME_PASSENGER_PATTERN.matcher(lastName).matches());

        do {
            System.out.println("Give your ID card:");
            idCard = scanner.nextLine();
        } while (!ID_CARD_PATTERN.matcher(idCard).matches());

        return Passenger.builder()
            .firstName(firstName)
            .lastName(lastName)
            .idCard(idCard)
            .build();
    }

    private String generateTicketId() {
        String ticketId;
        do {
            ticketId = UUID.randomUUID().toString();
        } while (isTicketIdPresent(ticketId));

        return ticketId;
    }

    private boolean isTicketIdPresent(String ticketId) {
        Reservation reservation = searchService.searchReservationByTicketId(ticketId);

        return reservation != null;
    }

    private Reservation createTicket() {
        String ticketId = generateTicketId();

        return Reservation.builder()
            .ticketId(ticketId)
            .build();
    }

    private Optional<Flight> chooseFlight(List<Flight> flights) {
        List<Long> flightIds = flights.stream()
            .map(Flight::getFlightId)
            .collect(Collectors.toList());

        System.out.println(flights);
        long flightId;
        do {
            System.out.println("Give flight id:");
            flightId = Long.parseLong(scanner.next());
        } while (!flightIds.contains(flightId));

        long finalFlightId = flightId;
        return flights.stream()
            .filter(flight -> flight.getFlightId() == finalFlightId)
            .findFirst();
    }

}