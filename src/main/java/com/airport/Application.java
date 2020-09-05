package com.airport;

import com.airport.service.SearchCriteria;
import com.airport.service.SearchService;
import java.time.Instant;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application {

    public static void main(String[] args) {
//
//        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
//        SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
//            .buildMetadata()
//            .buildSessionFactory();

//        SearchService searchService = new SearchService();
//        BookingService bookingService = new BookingService(searchService, new CreateService());
//        System.out.println("Flight ticket booking:");
//        System.out.println("Result: " + bookingService.bookFlight("ABC123456", "Warszawa", "Moskwa", Instant.parse("2020-08-19T16:30:00.Z"), 2));
//        DeleteService deleteService = new DeleteService(searchService);
//        System.out.println("Flight ticket cancel:");
//        System.out.println("Result: " + deleteService.cancelFlight("45ab017f-8bba-4cec-b6be-4d305b95d6fc"));

//       searchService.searchReservationByCardId("ABC123456");

        SearchService searchService = new SearchService();
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("Welcome to the flight service");

        do {
            System.out.println("Choose the options you want:\n" +
                "[1] Search flights\n" +
                "[2] Book flight\n" +
                "[3] Search your reservations\n" +
                "[4] Cancel flight\n" +
                "[q] Close application"
            );
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printChoice(choice);
                    SearchCriteria searchCriteria = prepareSearchCriteria();
                    searchService.searchFlightsByDynamicCriteria(
                        searchCriteria.getFromCity(),
                        searchCriteria.getToCity(),
                        searchCriteria.getDepartureTime(),
                        searchCriteria.getSeatsNumber());
                    break;
                case "2":
                    printChoice(choice);
                    break;
                case "3":
                    printChoice(choice);
                    break;
                case "4":
                    printChoice(choice);
                    break;
                case "q":
                    printChoice(choice);
                    break;
                default:
                    System.out.println("The selected option is invalid, please try again...");
            }
            scanner.nextLine();
        } while (!choice.equals("x"));
    }

    private static void printChoice(String choice) {
        System.out.println("You have selected:" + choice);
    }

    private static SearchCriteria prepareSearchCriteria() {
        final Pattern seatsNumberPattern = Pattern.compile("[0-9]{1,3}");
        final Pattern departureTimePattern = Pattern.compile("20[0-9]{2}" +
            "-[0][1-9]|[1][0-2]" +
            "-[0][1-9]|[1-2][0-9]|[3][0-1]" +
            "T[0][0-9]|[1][0-9]|[2][0-3]" +
            ":[0][0-9]|[10-59]" +
            ":[0][0-9]|[10-59]" +
            "Z");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a value of place of departure or press enter:");
        String fromCity = scanner.nextLine();
        System.out.println("Type a value of place of arrival or press enter:");
        String toCity = scanner.nextLine();
        String departureTime;
        do {
            System.out.println("Type a value of departure time or press enter (please keep the format: YYYY-MM-DDThh:mm:ssZ):");
            departureTime = scanner.nextLine();
        } while (!departureTimePattern.matcher(departureTime).matches());

        String seatsNumber;
        do {
            System.out.println("Type a value of seats number or press enter (please keep the format: nnn):");
            seatsNumber = scanner.nextLine();
        } while (!seatsNumberPattern.matcher(seatsNumber).matches());

        return SearchCriteria.builder()
            .fromCity(fromCity)
            .toCity(toCity)
            .departureTime(Instant.parse(departureTime))
            .seatsNumber(Integer.parseInt(seatsNumber))
            .build();
    }

}