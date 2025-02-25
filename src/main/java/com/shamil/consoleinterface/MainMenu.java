package com.shamil.consoleinterface;

import com.shamil.model.Customer;
import com.shamil.model.User;
import com.shamil.service.ReservationService;
import com.shamil.service.SpaceService;
import com.shamil.service.UserService;

import java.util.Scanner;

public class MainMenu extends AbstractMenu {
    private Scanner sc = new Scanner(System.in);
    private UserService userService;
    private SpaceService spaceService;
    private ReservationService reservationService;
    private CustomerMenu customerMenu;
    private AdminMenu adminMenu;

    public MainMenu(UserService userService, SpaceService spaceService, ReservationService reservationService, Scanner sc) {
        super(sc);
        this.userService = userService;
        this.spaceService = spaceService;
        this.reservationService = reservationService;
        this.customerMenu = new CustomerMenu(spaceService, reservationService, sc);
        this.adminMenu = new AdminMenu(spaceService, reservationService, sc);
    }

    @Override
    public void display() {
        boolean running = true;

        while (running) {
            displayHeader("COWORKING SPACE MANAGEMENT SYSTEM");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("0. Exit");

            int choice = getMenuChoice(0, 2);

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
                    break;
            }
        }
    }

    private void login() {
        displayHeader("LOGIN");

        System.out.println("USERNAME");
        String username = sc.nextLine();
        System.out.println("PASSWORD");
        String password = sc.nextLine();

        username = username.trim();
        password = password.trim();

        User user = userService.login(username, password);

        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getUsername());

            if (user.getRole().getRoleName().equals("ADMIN")) {
                adminMenu.display();
            } else if (user.getRole().getRoleName().equals("CUSTOMER")) {
                customerMenu.display((Customer) user);
            }

            userService.logout();
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private void register() {
        displayHeader("REGISTER NEW CUSTOMER");

        System.out.println("USERNAME");
        String username = sc.nextLine();
        System.out.println("PASSWORD");
        String password = sc.nextLine();

        username = username.trim();
        password = password.trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty");
            return;
        }

        Customer customer = userService.registerCustomer(username, password);

        if (customer != null) {
            System.out.println("Registration successful! Please login with your new account.");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
    }
}