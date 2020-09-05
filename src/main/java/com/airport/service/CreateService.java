package com.airport.service;

import com.airport.model.Passenger;
import com.airport.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

@RequiredArgsConstructor
public class CreateService {

    private final Session session = SessionSingleton.getSessionSingleton().getSession();

    public void registerPassenger(Passenger passenger) {
        session.save(passenger);
    }

    public void addReservation(Reservation reservation) {
        session.save(reservation);
    }

}