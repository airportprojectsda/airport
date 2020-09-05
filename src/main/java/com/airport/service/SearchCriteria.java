package com.airport.service;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SearchCriteria {

    private final String fromCity;
    private final String toCity;
    private final Instant departureTime;
    private final int seatsNumber;

}