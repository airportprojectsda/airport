package com.airport.service;

import com.airport.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

@RequiredArgsConstructor
public class DeleteService {

    private final Session session = SessionSingleton.getSessionSingleton().getSession();
    private final SearchService searchService;

    public boolean deleteReservation(String ticketId) {
        Transaction transaction = session.beginTransaction();

        Reservation reservation = searchService.searchReservationByTicketId(ticketId);
        if (reservation == null) {
            return false;
        }
        session.delete(reservation);
        int presentNumberOfVacancies = reservation.getFlight().getPlane().getNumberOfVacancies();
        reservation.getFlight().getPlane().setNumberOfVacancies(presentNumberOfVacancies + 1);

        transaction.commit();

        return true;
    }

}