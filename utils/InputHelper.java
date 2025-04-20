/* Class is to allow for reading integer input and ensuring that input is an int */
package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    private static Scanner sc = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = sc.nextInt();
                sc.nextLine(); // consume the leftover newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.nextLine(); // consume the invalid input
            }
        }
    }


    public static boolean confirm(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) return true;
            if (input.equalsIgnoreCase("N")) return false;
            System.out.println("Please enter 'Y' or 'N'.");
        }
    }

    public static LocalDate readDate(String prompt, DateTimeFormatter formatter) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.isEmpty()) {
                return null; // Return null if the user leaves the input blank
            }
            try {
                date = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the format DD/MM/YYYY.");
            }
        }
        return date;
    }

    public static float readFloat(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                float value = sc.nextFloat();
                sc.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear invalid input
            }
        }
    }

}