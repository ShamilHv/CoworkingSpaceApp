package com.shamil.consoleinterface;

import java.util.Scanner;


public abstract class AbstractMenu {
    protected Scanner scanner;

    public AbstractMenu(Scanner scanner) {
        this.scanner = scanner;
    }


    public abstract void display();

    protected int getMenuChoice(int min, int max) {
        int choice;
        while (true) {
            System.out.println("Select an option between " + min + " and " + max);
            try {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            }
        }
    }


    protected void displayHeader(String title) {
        System.out.println("\n" + title);
        System.out.println("=".repeat(title.length()));
    }

}
