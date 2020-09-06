package com.airport.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    @Column
    private String name;

    @Column
    private String cityName;

    @OneToMany(mappedBy = "airport")
    private List<Flight> flights;

    @Override
    public String toString() {
        return "[Name: '" + name + "'\n" +
            "City Name: '" + cityName + "']\n";
    }

}