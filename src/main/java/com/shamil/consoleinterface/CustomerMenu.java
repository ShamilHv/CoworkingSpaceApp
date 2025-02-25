package com.shamil.consoleinterface;

import com.shamil.model.Customer;
import com.shamil.model.Reservation;
import com.shamil.model.Space;
import com.shamil.service.ReservationService;
import com.shamil.service.SpaceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu extends AbstractMenu {
    private Scanner sc = new Scanner(System.in);
    private SpaceService spaceService;
    private ReservationService reservationService;

    public CustomerMenu(SpaceService spaceService, ReservationService reservationService, Scanner sc) {
        super(sc);
        this.spaceService = spaceService;
        this.reservationService = reservationService;
    }

    @Override
    public void display() {
        System.out.println("Error: Customer information is required. Please log in first.");
    }

    public void display(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Customer information is missing.");
            return;
        }

        System.out.println("Welcome, " + customer.getUsername() + "!");

        boolean running = true;
        while (running) {
            displayHeader("CUSTOMER MENU");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("0. Logout");

            int choice = getMenuChoice(0, 4);

            switch (choice) {
                case 1:
                    browseAvailableSpaces();
                    break;
                case 2:
                    makeReservation(customer);
                    break;
                case 3:
                    viewMyReservations(customer.getId());
                    break;
                case 4:
                    cancelReservation(customer);
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    private void browseAvailableSpaces() {
        displayHeader("AVAILABLE SPACES");

        List<Space> spaces = spaceService.getAvailableSpaces();

        if (spaces.isEmpty()) {
            System.out.println("No spaces available at the moment.");
            return;
        }

        for (Space space : spaces) {
            System.out.println(space);
        }
    }

    private void makeReservation(Customer customer) {
        displayHeader("MAKE A RESERVATION");

        List<Space> spaces = spaceService.getAvailableSpaces();

        if (spaces.isEmpty()) {
            System.out.println("No spaces available for reservation.");
            return;
        }

        System.out.println("Available Spaces:");
        for (Space space : spaces) {
            System.out.println(space);
        }

        System.out.println("Enter Space Name:");
        String spaceName = sc.nextLine();

        Space selectedSpace = spaceService.getSpaceByName(spaceName);
        if (selectedSpace == null) {
            System.out.println("Invalid space NAME or space is no longer available");
            return;
        }

        LocalDateTime startDateTime = ConsoleUtils.getDateTime(
                "Enter reservation start date", "Enter reservation start time", sc);

        LocalDateTime endDateTime = ConsoleUtils.getDateTime(
                "Enter reservation end date", "Enter reservation end time", sc);

        if (startDateTime.isAfter(endDateTime)) {
            System.out.println("End time must be after start time");
            return;
        }

        if (startDateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Start time must be in the future");
            return;
        }

        Reservation reservation = reservationService.makeReservation(
                customer, spaceName, startDateTime, endDateTime);

        if (reservation != null) {
            System.out.println("Reservation created successfully!");
            System.out.println("Reservation details: " + reservation);
        } else {
            System.out.println("Failed to create reservation");
        }
    }

    private void viewMyReservations(String customerId) {
        displayHeader("MY RESERVATIONS");

        List<Reservation> reservations = reservationService.getCustomerReservations(customerId);

        if (reservations.isEmpty()) {
            System.out.println("You don't have any reservations.");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private void cancelReservation(Customer customer) {
        displayHeader("CANCEL RESERVATION");

        List<Reservation> reservations = reservationService.getCustomerReservations(customer.getId());

        if (reservations.isEmpty()) {
            System.out.println("You don't have any reservations to cancel.");
            return;
        }

        System.out.println("Your Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }

        System.out.println("Enter reservation ID to cancel");
        String reservationId = sc.nextLine();

        boolean cancelled = reservationService.cancelReservation(reservationId, customer);

        if (cancelled) {
            System.out.println("Reservation cancelled successfully");
        } else {
            System.out.println("Failed to cancel reservation. ID might be invalid or already cancelled.");
        }
    }
}