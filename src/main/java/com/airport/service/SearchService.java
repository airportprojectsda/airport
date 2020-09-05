package com.airport.service;

import com.airport.model.Flight;
import com.airport.model.Passenger;
import com.airport.model.Plane;
import com.airport.model.Reservation;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

@RequiredArgsConstructor
public class SearchService {

    private final Session session = SessionSingleton.getSessionSingleton().getSession();

    public Passenger searchPassengerByIdCard(String idCard) {
        Query<Passenger> query = session.createQuery("from Passenger p where p.idCard =: idCard", Passenger.class);
        query.setParameter("idCard", idCard);

        return query.uniqueResult();
    }

    public Reservation searchTicketByTicketId(String ticketId) {
        Query<Reservation> query = session.createQuery("from Reservation t where t.ticketId =: ticketId", Reservation.class);
        query.setParameter("ticketId", ticketId);

        return query.uniqueResult();
    }

    public List<Flight> searchFlightsByDynamicCriteria(String fromCity, String toCity, Instant departureTime, int seatsNumber) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> flightCriteriaQuery = criteriaBuilder.createQuery(Flight.class);

        Root<Flight> fromFlight = flightCriteriaQuery.from(Flight.class);

        flightCriteriaQuery.select(fromFlight).where(fromCityPredicate(fromCity, criteriaBuilder, fromFlight),
            toCityPredicate(toCity, criteriaBuilder, fromFlight),
            departureTimePredicate(departureTime, criteriaBuilder, fromFlight),
            seatsNumberPredicate(seatsNumber, criteriaBuilder, flightCriteriaQuery))
            .distinct(true);
        Query<Flight> query = session.createQuery(flightCriteriaQuery);

        return query.getResultList();
    }

    private Predicate fromCityPredicate(String fromCity, CriteriaBuilder criteriaBuilder, Root<Flight> root) {
        if (Objects.nonNull(fromCity)) {
            return criteriaBuilder.equal(root.get("fromCity"), fromCity);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Predicate toCityPredicate(String toCity, CriteriaBuilder criteriaBuilder, Root<Flight> root) {
        if (Objects.nonNull(toCity)) {
            return criteriaBuilder.equal(root.get("toCity"), toCity);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Predicate departureTimePredicate(Instant departureTime, CriteriaBuilder criteriaBuilder, Root<Flight> root) {
        if (Objects.nonNull(departureTime)) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("departureTime"), departureTime);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Predicate seatsNumberPredicate(Integer seatsNumber, CriteriaBuilder criteriaBuilder, CriteriaQuery<Flight> flightCriteriaQuery) {
        Root<Plane> fromPlane = flightCriteriaQuery.from(Plane.class);

        if (Objects.nonNull(seatsNumber)) {
            return criteriaBuilder.greaterThanOrEqualTo(fromPlane.get("numberOfVacancies"), seatsNumber);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

}