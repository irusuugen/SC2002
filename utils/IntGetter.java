/* Class is to allow for reading integer input and ensuring that input is an int */
package utils;

import java.util.Scanner;

public class IntGetter {
    public static int readInt(String prompt) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // Discard invalid input
            }
        }
    }
}