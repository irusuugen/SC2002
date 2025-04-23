/**
 * This class handles password change operations for all user accounts.
 */

package boundary;

import entity.*;
import repository.UserService;

import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import utils.*;

public class ChangeAccountPassword {
    /**
     * Guides the user through the password change process with validation.
     * Verifies current password, validates new password against security requirements,
     * and updates the password if all conditions are met.
     *
     * @param user The user object whose password will be changed
     */

    public static void changePassword(User user) {
        String newPassword;
        String newPassword2;
        Scanner sc = new Scanner(System.in);
        String oldPassword = user.getPassword();

        ClearPage.clearPage();
        displayPasswordRequirements();

        BoxPrinter.printTopBorder();
        System.out.println(BoxPrinter.centerInBox("Change Password"));
        BoxPrinter.printBottomBorder();

        System.out.print("Please enter your current password: ");
        String currentPassword = sc.nextLine();
        
        if (!currentPassword.equals(oldPassword)) {
            System.out.println("Incorrect current password!");
            return;
        }
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
                UserService.updateUsers(user);
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

    /**
     * Displays all criteria that must be met for a valid password
     * to the user
     */
    private static void displayPasswordRequirements() {
        System.out.println("Your new password must meet the following requirements:");
        System.out.println("• At least 12 characters long");
        System.out.println("• At least one uppercase letter (A-Z)");
        System.out.println("• At least one lowercase letter (a-z)");
        System.out.println("• At least one digit (0-9)");
        System.out.println("• At least one special character (e.g., !@#$%^&*)");
        System.out.println("• No spaces allowed");
        System.out.println();
    }

    /**
     * Validates a password against security requirements (length, character diversity, etc.)
     *
     * @param password The password string to validate
     * @return true if the password meets all requirements, false otherwise
     */
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
