package boundary;

import entity.*;
import utils.*;
import java.util.Scanner;

public class ApplicantMenu {
    public static void applicantMenu(User user) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {  // Main menu loop
            ClearPage.clearPage();
            System.out.println("""
            ╔════════════════════════════════════════════╗
            ║              Applicant Menu                ║
            ╠════════════════════════════════════════════╣
            ║  1. Change password                        ║
            ║  2. View all open projects                 ║
            ║  3. Apply for a project                    ║
            ║  4. View application                       ║
            ║  5. Book flat                              ║
            ║  6. Request withdrawal for application     ║
            ║  7. Submit enquiry about a project         ║
            ║  8. View enquiries                         ║
            ║  9. Edit enquiries                         ║
            ║  10. Delete enquiries                      ║
            ║  11. Logout                                ║
            ╚════════════════════════════════════════════╝
            """);
            
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();  // Consume the newline character
                
            switch (choice) {
                case 1:
                    // Change password functionality
                    System.out.println("Change password selected");
                    break;
                case 2:
                    // View projects functionality
                    System.out.println("View projects selected");
                    break;
                // Add cases 3-10 similarly
                case 11:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1-11.");
            }
        }
    }
}