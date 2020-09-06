package com.airport;

import com.airport.service.BookingService;
import com.airport.service.CreateService;
import com.airport.service.DeleteService;
import com.airport.service.SearchCriteria;
import com.airport.service.SearchService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
        CreateService createService = new CreateService();
        DeleteService deleteService = new DeleteService(searchService);
        BookingService bookingService = new BookingService(searchService, createService);
        Scanner scanner = new Scanner(System.in);
        String overalChoice;
        System.out.println("Welcome to the flight service");

        do {
            System.out.println("Choose the options you want:\n" +
                "[1] Search flights\n" +
                "[2] Book flight\n" +
                "[3] Search your reservations\n" +
                "[4] Cancel flight\n" +
                "[q] Close application"
            );
            overalChoice = scanner.nextLine();
            scanner.nextLine();
            String idCard;
            String result;

            switch (overalChoice) {
                case "1":
                    printChoice(overalChoice);
                    SearchCriteria flightCriteria = prepareSearchCriteria(scanner, true);
                    System.out.println("RESULT: \n" +
                        searchService.searchFlightsByDynamicCriteria(
                            flightCriteria.getFromCity(),
                            flightCriteria.getToCity(),
                            flightCriteria.getDepartureTime(),
                            flightCriteria.getSeatsNumber()));
                    break;
                case "2":
                    printChoice(overalChoice);
                    idCard = getIdCard(scanner);
                    String fromCity = getFromCity(scanner);
                    String toCity = getToCity(scanner);
                    Instant departureTime = getDepartureTime(scanner);
                    int seatNumber = getSeatsNumber(scanner);
                    result = bookingService.bookFlight(idCard, fromCity, toCity, departureTime, seatNumber)
                        ? "YOU HAVE BOOK FLIGHT"
                        : "IS NOT POSSIBLE TO BOOK FLIGHT";
                    System.out.println("RESULT: ");
                    System.out.println(result);
                    break;
                case "3":
                    printChoice(overalChoice);
                    String reservationChoice;
                    do {
                        System.out.println("Choose the search reservation option you want:\n" +
                            "[1] Search reservation by id card\n" +
                            "[2] Search reservation by id card and flight\n" +
                            "[b] Back to main menu"
                        );
                        reservationChoice = scanner.nextLine();
                        scanner.nextLine();

                        switch (reservationChoice) {
                            case "1":
                                printChoice(reservationChoice);
                                idCard = getIdCard(scanner);
                                System.out.println("RESULT: \n" + searchService.searchReservationByCardId(idCard));
                                break;
                            case "2":
                                printChoice(reservationChoice);
                                idCard = getIdCard(scanner);
                                SearchCriteria reservationCriteria = prepareSearchCriteria(scanner, false);
                                System.out.println("RESULT: \n" +
                                    searchService.searchReservationByCardIdAndFlightCriteria(
                                        idCard,
                                        reservationCriteria.getFromCity(),
                                        reservationCriteria.getToCity(),
                                        reservationCriteria.getDepartureTime(),
                                        reservationCriteria.getSeatsNumber()));
                                break;
                            case "b":
                                printChoice(overalChoice);
                                System.out.println("You will be back to main menu");
                                break;
                            default:
                                System.out.println("The selected option is invalid, please try again...");
                        }
                    } while (!reservationChoice.equals("b"));
                    break;
                case "4":
                    printChoice(overalChoice);
                    String ticketId = getTicketId(scanner);
                    result = deleteService.deleteReservation(ticketId)
                        ? "RESERVATION HAS BEEN DELETED"
                        : "RESERVATION CAN'T BE DELETED";
                    System.out.println(result);
                    break;
                case "q":
                    printChoice(overalChoice);
                    System.out.println("The service will be closed");
                    break;
                default:
                    System.out.println("The selected option is invalid, please try again...");
            }
        } while (!overalChoice.equals("q"));
    }

    private static void printChoice(String choice) {
        System.out.println("You have selected: " + choice);
    }

    private static SearchCriteria prepareSearchCriteria(Scanner scanner, boolean isSeatsNumberImportant) {
        int seatsNumber = isSeatsNumberImportant
            ? getSeatsNumber(scanner)
            : 0;

        return SearchCriteria.builder()
            .fromCity(getFromCity(scanner))
            .toCity(getToCity(scanner))
            .departureTime(getDepartureTime(scanner))
            .seatsNumber(seatsNumber)
            .build();
    }

    private static int getSeatsNumber(Scanner scanner) {
        final Pattern seatsNumberPattern = Pattern.compile("[0-9]{1,3}");
        String seatsNumber;
        do {
            System.out.println("Type a value of seats number or press enter (please keep the format: nnn):");
            seatsNumber = scanner.nextLine();
        } while (!seatsNumberPattern.matcher(seatsNumber).matches());

        return Integer.parseInt(seatsNumber);
    }

    private static String getFromCity(Scanner scanner) {
        System.out.println("Type a value of place of departure or press enter:");
        return scanner.nextLine();
    }

    private static String getToCity(Scanner scanner) {
        System.out.println("Type a value of place of arrival or press enter:");
        return scanner.nextLine();
    }

    private static String getTicketId(Scanner scanner) {
        System.out.println("Type a value of ticket id:");
        return scanner.nextLine();
    }

    private static String getIdCard(Scanner scanner) {
        final Pattern idCardPattern = Pattern.compile("[a-zA-Z]{3}[0-9]{6}");

        String idCard;
        do {
            System.out.println("Type a value of idCard:");
            idCard = scanner.nextLine();
        } while (!idCardPattern.matcher(idCard).matches());

        return idCard;
    }

    private static Instant getDepartureTime(Scanner scanner) {
        final Pattern departureTimePattern = Pattern.compile("" +
            "(?<year>\\d{4})-" +
            "(?<month>[0][1-9]|[1][0-2])-" +
            "(?<day>[0][1-9]|[1-2][0-9]|[3][0-1]) " +
            "(?<hour>[0][0-9]|[1][0-9]|[2][0-3]):" +
            "(?<minute>[0][0-9]|[1-5][0-9])");
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String departureTime;
        do {
            System.out.println("Type a value of departure time or press enter (please keep the format: YYYY-MM-DD hh:mm):");
            departureTime = scanner.nextLine();
        } while (!departureTimePattern.matcher(departureTime).matches());

        return LocalDateTime.parse(departureTime, formatter).toInstant(ZoneOffset.UTC);
    }

}