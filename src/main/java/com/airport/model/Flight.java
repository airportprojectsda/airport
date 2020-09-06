package com.airport.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column
    private String fromCity;

    @Column
    private String toCity;

    @Column
    private Instant departureTime;

    @Column
    private Instant arrivalTime;

    @Column
    private BigDecimal price;

    @ManyToOne
    private Airport airport;

    @ManyToOne
    private Plane plane;

    @ManyToMany
    @JoinTable(
        name = "flight_passenger",
        joinColumns = @JoinColumn(name = "flight_id"),
        inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private List<Passenger> passengers;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "flight_id")
    List<Reservation> reservations;

    @Override
    public String toString() {
        return "\nFlight Id: " + flightId + ":\n" +
            " [From City: '" + fromCity + "'\n" +
            " To City: '" + toCity + "'\n" +
            " Departure Time: " + departureTime + "\n" +
            " Arrival Time: " + arrivalTime + "\n" +
            " Price: " + price + "\n" +
            " Airport: " + airport + "\n" +
            " Plane: " + plane + "]";
    }

}