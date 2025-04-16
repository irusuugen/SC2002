package boundary;

import control.*;
import entity.*;
import java.util.Scanner;
import utils.*;

public class ApplicantMenu {
    public static void applicantMenu(Applicant applicant) {
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
                
            switch (choice) {
                case 1:
                    // Change password functionality (not implemented yet)
                    break;
                case 2:
                    // View projects functionality
                    ApplicantController.viewOpenProjects(applicant);
                    System.out.println("Press enter to go back");
                    sc.nextLine();
                    break;
                case 3:
                    // Apply for project
                    break;
                case 4:
                    // View application
                    break;
                case 5:
                    // Book with officer
                    break;
                case 6:
                    // Request withdrawal
                    break;
                case 7:
                    // Submit enquiry
                    break;
                case 8:
                    // View enquiry
                    break;
                case 9:
                    // Edit enquiry
                    break;
                case 10:
                    // Delete enquiry
                    break;
                case 11:
                    // Log out
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1-11.");
            }
        }
    }
}