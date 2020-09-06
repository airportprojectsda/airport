package com.airport.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planeId;

    @Column
    private String model;

    @Column
    private Integer totalNumberOfSeats;

    @Column
    private Integer numberOfVacancies;

    @OneToMany(mappedBy = "plane", orphanRemoval = true)
    private List<Flight> flights;

    @ManyToOne
    private Airline airline;

    @Override
    public String toString() {
        return "Plane:\n" +
            "[Model: '" + model + "'\n" +
            "Number Of Vacancies: " + numberOfVacancies + "\n" +
            "Airline: " + airline + "]";
    }

}