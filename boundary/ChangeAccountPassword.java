package boundary;

import entity.*;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import utils.*;

public class ChangeAccountPassword {
    public static void changePassword(Role role, User user) {
        String newPassword;
        String newPassword2;
        Scanner sc = new Scanner(System.in);
        String oldPassword = user.getPassword();

        ClearPage.clearPage();
        displayPasswordRequirements();

        while (true) {
            System.out.print("Please enter your new password: ");
            newPassword = sc.nextLine();
            System.out.print("Please enter your new password again: ");
            newPassword2 = sc.nextLine();

            if (!newPassword.equals(newPassword2)) {
                System.out.println("Your passwords do not match. Please try again.");
            } else if (newPassword.equals(oldPassword)) {
                System.out.println("New password cannot be the same as the old password.");
            } else if (!isValidPassword(newPassword)) {
                // Error message is printed within isValidPassword
            } else {
                user.changePassword(newPassword);
                System.out.println("Password updated successfully.");
                System.out.println("Please log in again with your new password.");
                System.out.println("Returning to home screen...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    private static void displayPasswordRequirements() {
        System.out.println("Your new password must meet the following requirements:");
        System.out.println("• At least 12 characters long");
        System.out.println("• At least one uppercase letter (A-Z)");
        System.out.println("• At least one lowercase letter (a-z)");
        System.out.println("• At least one digit (0-9)");
        System.out.println("• At least one special character (e.g., !@#$%^&*)");
        System.out.println("• No spaces allowed");
        System.out.println("• Cannot be a commonly used password");
        System.out.println();
    }

    private static boolean isValidPassword(String password) {
        if (password.length() < 12) {
            System.out.println("Password must be at least 12 characters long.");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("Password must contain at least one lowercase letter.");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            System.out.println("Password must contain at least one digit.");
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()\\-+=<>?/\\\\\\[\\]{}|~`].*")) {
            System.out.println("Password must contain at least one special character.");
            return false;
        }
        if (password.contains(" ")) {
            System.out.println("Password must not contain spaces.");
            return false;
        }
        return true;
    }
}
