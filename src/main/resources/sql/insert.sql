insert into airline (name) values ('SpainAir'), ('AirItaly'),('PLL_LOT'), ('Lufthanza'), ('PutinAir');

insert into airport (cityName, name) values ('Warszawa', 'ChopinAirport'), ('Berlin', 'Brandenburg'), ('Moskwa', 'Szeremietewo'),
('Madryt', 'ComunityMadrid'), ('Mediolan', 'MilanAirPort');

insert into plane (model, numberOfVacancies, totalNumberOfSeats,airline_airlineId )
 values ('Boeing525', 140, 140, 4), ('Boeing333', 100,100, 5),('Boeing747', 180, 180,  1), ('AirbusC30', 220, 220, 2), ('ATR300', 130, 130, 3);

insert into flight (departureTime, arrivalTime, fromCity, price, toCity, airport_airportId, plane_planeId)
values ('2020-08-19 09:45', '2020-08-19 12:45', 'Moskwa', 299, 'Berlin', 3, 3),
('2020-08-19 17:30', '2020-08-19 20:30', 'Warszawa', 199, 'Moskwa', 1, 1),
('2020-08-19 15:00', '2020-08-19 16:30', 'Berlin', 150, 'Warszawa', 2, 1),
('2020-08-19 16:30', '2020-08-19 18:30', 'Warszawa', 399, 'Moskwa', 1, 1),
('2020-08-20 14:00', '2020-08-20 15:15', 'Warszawa', 99, 'Berlin', 1, 2),
('2020-08-19 08:30', '2020-08-19 11:30', 'Berlin', 250, 'Moskwa', 2, 3),
('2020-08-19 12:45', '2020-08-19 15:45', 'Madryt', 199, 'Berlin', 4, 4),
('2020-08-19 16:00', '2020-08-19 18:45', 'Mediolan', 499, 'Moskwa', 5, 5);ó