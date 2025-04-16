package boundary;

import entity.*;
import java.util.Scanner;
import utils.*;

public class ChangeAccountPassword {
    public void changePassword(Role role, User user) {
        String newPassword;
        String newPassword2;
        Scanner sc = new Scanner(System.in);
        String oldPassword = user.getPassword();

        ClearPage.clearPage();
        while (true) { 
            System.out.print("Please enter your new password: ");
            newPassword = sc.nextLine();
            System.out.print("Please enter your new password again: ");
            newPassword2 = sc.nextLine();
            if (!newPassword.equals(newPassword2)) {
                System.out.println("Your passwords are not the same. Please try again.");
            } else if (newPassword.equals(oldPassword)) {
                System.out.println("New password cannot be the same as the old password. Please try again");
            } else {
                user.changePassword(newPassword);
                System.out.println("Password updated successfully.");
                System.out.println("Please log in again with your new password.");
                System.out.println("Returning to home screen...");
                try {
                    Thread.sleep(1000); // Optional: just to give user a moment before redirection
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}