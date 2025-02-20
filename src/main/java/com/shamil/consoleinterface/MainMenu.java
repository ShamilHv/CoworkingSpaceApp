package com.shamil.consoleinterface;

import com.shamil.model.Admin;
import com.shamil.model.Customer;
import com.shamil.model.User;
import com.shamil.service.ReservationService;
import com.shamil.service.SpaceService;
import com.shamil.service.UserService;

import java.util.Scanner;


public class MainMenu {
    private  Scanner sc = new Scanner(System.in);
    private UserService userService;
    private SpaceService spaceService;
    private ReservationService reservationService;
    private CustomerMenu customerMenu;
    private AdminMenu adminMenu;


    public MainMenu(UserService userService, SpaceService spaceService, ReservationService reservationService) {
        this.userService = userService;
        this.spaceService = spaceService;
        this.reservationService = reservationService;
        this.customerMenu = new CustomerMenu(spaceService, reservationService);
        this.adminMenu = new AdminMenu(spaceService, reservationService);
    }

    public void display() {
        boolean running = true;

        while (running) {
            System.out.println("COWORKING SPACE MANAGEMENT SYSTEM");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("0. Exit");

            int choice;
            while (true) {
                System.out.println("Select an option between 0 and 2");
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Add this line to consume newline
                    if (choice >= 0 && choice <= 2) {
                        break;
                    } else {
                        System.out.println("Invalid option");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a number");
                    sc.nextLine(); // Already correct here
                }
            }

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Coworking Space Management System!");
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void login() {
        System.out.println("LOGIN");

        System.out.println("USERNAME");
        String username = sc.nextLine();
        System.out.println("PASSWORD");
        String password = sc.nextLine();

        User user = userService.login(username, password);

        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getUsername());

            if (user instanceof Admin) {
                adminMenu.display();
            } else if (user instanceof Customer) {
                customerMenu.display((Customer) user);
            }

            userService.logout();
        } else {
            System.out.println("Invalid username or password");
        }
    }
    private void register() {
        System.out.println("REGISTER NEW CUSTOMER");

        System.out.println("USERNAME");
        String username = sc.nextLine();
        System.out.println("PASSWORD");
        String password = sc.nextLine();

        Customer customer = userService.registerCustomer(username, password);

        if (customer != null) {
            System.out.println("Registration successful! Please login with your new account.");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
    }
}

