package com.airport.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String idCard;

    @OneToMany(orphanRemoval = true)//(mappedBy = "passenger")
    @JoinColumn(name = "passenger_id")
    private List<Reservation> reservations;

    @ManyToMany(mappedBy = "passengers")
    private List<Flight> flights;

    @Override
    public String toString() {
        return "Passenger:\n" +
            "[First Name: '" + firstName + "'\n" +
            "Last Name: '" + lastName + "'\n" +
            "Id Card: '" + idCard + "']";
    }

}