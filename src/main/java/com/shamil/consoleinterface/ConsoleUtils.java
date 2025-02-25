package com.shamil.consoleinterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleUtils {
    public static LocalDate getDate(String date, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print(date + " (yyyy-MM-dd): ");
                String input = sc.nextLine().trim();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date in the format yyyy-MM-dd");
            }
        }
    }
    public static LocalTime getTime(String time,  Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        while (true) {
            try {
                System.out.print(time + " (HH:mm): ");
                String input = sc.nextLine().trim();
                return LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid time in the format HH:mm (24-hour)");
            }
        }
    }
    public static LocalDateTime getDateTime(String datePrompt, String timePrompt,  Scanner sc) {
        LocalDate date = getDate(datePrompt, sc);
        LocalTime time = getTime(timePrompt, sc);
        return LocalDateTime.of(date, time);
    }
}