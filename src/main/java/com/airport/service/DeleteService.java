package com.airport.service;

import com.airport.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

@RequiredArgsConstructor
public class DeleteService {

    private final Session session = SessionSingleton.getSessionSingleton().getSession();
    private final SearchService searchService;

    public boolean cancelFlight(String ticketId) {
        Transaction transaction = session.beginTransaction();

        Reservation reservation = searchService.searchTicketByTicketId(ticketId);
        if (reservation == null) {
            return false;
        }
        session.delete(reservation);

        transaction.commit();

        return true;
    }

}