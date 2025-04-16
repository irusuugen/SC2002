/* Class is to allow for reading integer input and ensuring that input is an int */
package utils;

import java.util.Scanner;

public class IntGetter {
    public static int readInt() {
        try {
            return new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer.");
            return readInt();
        }
    }
}