package com.shamil.consoleinterface;

import com.shamil.enums.SpaceType;
import com.shamil.model.Reservation;
import com.shamil.model.Space;
import com.shamil.service.ReservationService;
import com.shamil.service.SpaceService;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private SpaceService spaceService;
    private ReservationService reservationService;
    private  Scanner sc = new Scanner(System.in);


    public AdminMenu(SpaceService spaceService, ReservationService reservationService) {
        this.spaceService = spaceService;
        this.reservationService = reservationService;
    }
    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("ADMIN MENU");
            System.out.println("1. Add a new coworking space");
            System.out.println("2. View all coworking spaces");
            System.out.println("3. Remove a coworking space");
            System.out.println("4. View all reservations");
            System.out.println("0. Logout");

            int choice;
            while(true){
                System.out.println("Select an option between 0 and 4");
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if(choice > 4 || choice < 0){
                        System.out.println("Invalid option");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a valid number");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    addSpace();
                    break;
                case 2:
                    viewAllSpaces();
                    break;
                case 3:
                    removeSpace();
                    break;
                case 4:
                    viewAllReservations();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private void addSpace() {
        System.out.println("ADD NEW COWORKING SPACE");

        System.out.println("NAME OF SPACE");
        String name = sc.nextLine();

        System.out.println("Space Types:");
        for (int i = 0; i < SpaceType.values().length; i++) {
            System.out.println((i+1) + ". " + SpaceType.values()[i].getSpaceName());
        }

        System.out.println("Select a coworking space type");
        int typeChoice = sc.nextInt();
        SpaceType type = SpaceType.values()[typeChoice - 1];
        System.out.println("Enter hourly price ($)");
        double price = sc.nextDouble();
        sc.nextLine();

        Space newSpace = spaceService.addSpace(name, type, price);

        if (newSpace != null) {
            System.out.println("New space added successfully!");
        } else {
            System.out.println("Failed to add new space");
        }
    }
    private void viewAllSpaces() {
        System.out.println("ALL COWORKING SPACES");

        List<Space> spaces = spaceService.getAllSpaces();

        if (spaces.isEmpty()) {
            System.out.println("No spaces available.");
            return;
        }

        for (Space space : spaces) {
            System.out.println(space);
        }
    }
    private void removeSpace() {
        System.out.println("REMOVE COWORKING SPACE");

        List<Space> spaces = spaceService.getAllSpaces();

        if (spaces.isEmpty()) {
            System.out.println("No spaces available to remove.");
            return;
        }

        System.out.println("All Spaces:");
        for (Space space : spaces) {
            System.out.println(space);
        }

        System.out.println("Enter space name to remove");
        String spaceId = sc.nextLine();

        boolean removed = spaceService.removeSpace(spaceId);

        if (removed) {
            System.out.println("Space removed successfully");
        } else {
            System.out.println("Failed to remove space. ID might be invalid.");

        }
    }
    private void viewAllReservations() {
        System.out.println("ALL RESERVATIONS");

        List<Reservation> reservations = reservationService.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found in the system.");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

}


