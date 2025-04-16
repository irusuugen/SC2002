package boundary;

import control.*;
import entity.*;
import java.util.Scanner;
import utils.*;

public class ApplicantMenu {
    public static void applicantMenu(Applicant applicant) {
        System.out.println("DEBUG: REACHED APPLICANT MENU");
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
            sc.nextLine();
                
            switch (choice) {
                case 1:
                    // Change password functionality (not implemented yet)
                    break;
                case 2:
                    // View projects functionality
                    ClearPage.clearPage();
                    ApplicantController.viewOpenProjects(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 3:
                    // Apply for project
                    ClearPage.clearPage();
                    ApplicantController.applyForProject(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 4:
                    // View application
                    ClearPage.clearPage();
                    ApplicantController.viewApplication(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 5:
                    // Book with officer
                    break;
                case 6:
                    // Request withdrawal

                    break;
                case 7:
                    // Submit enquiry
                    ApplicantController.submitEnquiry(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 8:
                    // View enquiry
                    ApplicantController.viewEnquiries(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 9:
                    // Edit enquiry
                    ApplicantController.viewEnquiries(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 10:
                    // Delete enquiry
                    ApplicantController.deleteEnquiry(applicant);
                    System.out.println("Press Enter to go back");
                    sc.nextLine(); 
                    break;
                case 11:
                    // Log out
                    System.out.println("Logging out...");
                    try {
                        Thread.sleep(1000); 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1-11.");
            }
        }
    }
}