/**
 * A utility class for handling user input with validation.
 * Provides methods to read different types of input from the user with appropriate error handling.
 */

package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Reads an integer value from the user with validation.
     * Continues prompting until a valid integer is entered.
     *
     * @param prompt The message to display when asking for input
     * @return The valid integer value entered by the user
     */
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

    /**
     * Prompts the user for a yes/no confirmation.
     * Continues prompting until either 'Y' or 'N' is entered (case insensitive).
     *
     * @param message The confirmation message to display
     * @return true if user enters 'Y', false if user enters 'N'
     */
    public static boolean confirm(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) return true;
            if (input.equalsIgnoreCase("N")) return false;
            System.out.println("Please enter 'Y' or 'N'.");
        }
    }

    /**
     * Reads a date from the user with validation.
     * Allows for empty input (returns null) and validates the date format.
     *
     * @param prompt The message to display when asking for input
     * @param formatter The DateTimeFormatter to use for parsing the date
     * @return The parsed LocalDate, or null if input was empty
     */
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

    /**
     * Reads a float value from the user with validation.
     * Continues prompting until a valid float is entered.
     *
     * @param prompt The message to display when asking for input
     * @return The valid float value entered by the user
     */
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