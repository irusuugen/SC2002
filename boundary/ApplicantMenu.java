package boundary;

import control.*;
import entity.*;
import java.util.Scanner;
import utils.*;

public class ApplicantMenu {
    public static void applicantMenu(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {  // Applicant menu loops until logging out
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

            // Obtaining user's choice
            int choice;
            while (true) {
                choice = IntGetter.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 11) break;
                System.out.println("Please enter a number between 1 and 11.");
            }
            ClearPage.clearPage(); // Clears terminal for the page for the corresponding options
                
            switch (choice) {
                case 1:
                    // Change password (to be implemented)
                    break;
                case 2:
                    // View projects
                    ApplicantController.viewOpenProjects(applicant);
                    break;
                case 3:
                    // Apply for project
                    ApplicantController.applyForProject(applicant);
                    break;
                case 4:
                    // View application
                    ApplicantController.viewApplication(applicant);
                    break;
                case 5:
                    // Book with officer
                    ApplicantController.requestBooking(applicant);
                    break;
                case 6:
                    // Request withdrawal
                    ApplicantController.requestWithdrawal(applicant);
                    break;
                case 7:
                    // Submit enquiry
                    ApplicantController.submitEnquiry(applicant);
                    break;
                case 8:
                    // View enquiry
                    ApplicantController.viewEnquiries(applicant);
                    break;
                case 9:
                    // Edit enquiry
                    ApplicantController.editEnquiry(applicant);
                    break;
                case 10:
                    // Delete enquiry
                    ApplicantController.deleteEnquiry(applicant);
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
            }
            System.out.println("➤ Press Enter to go back.");
            sc.nextLine(); 
        }
    }
}