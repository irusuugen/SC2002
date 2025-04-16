package boundary;

import control.*;
import entity.*;
import java.util.Scanner;
import utils.*;

public class HDBOfficerMenu {
    public static void hdbOfficerMenu(HDBOfficer officer) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {  // Main menu loop
            ClearPage.clearPage();
            System.out.println("""
            ╔════════════════════════════════════════════╗
            ║               Officer Menu                 ║
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
            ║  11. Register to join a project            ║
            ║  12. View registration                     ║
            ║  13. View handling project                 ║
            ║  14. View enquiries on handling project    ║
            ║  15. Reply enquiries on handling project   ║
            ║  16. Update successful application         ║
            ║  17. Logout                                ║
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
                    ApplicantController.viewOpenProjects(officer);
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
                    HDBOfficerRegistrationController.registerForProject(officer);
                    System.out.println("Press Enter to go back");
                    sc.nextLine();
                    break;
                case 12:
                    HDBOfficerRegistrationController.registerForProject(officer);
                    System.out.println("Press Enter to go back");
                    sc.nextLine();
                    break;
                case 13:
                    ProjectViewer.printOneProject(officer.getAssignedProject(), officer);
                    System.out.println("Press Enter to go back");
                    sc.nextLine();
                    break;
                case 14:
                    HDBOfficerEnquiryHandler.viewHandlingProjectEnquiries(officer);
                    System.out.println("Press Enter to go back");
                    sc.nextLine();
                    break;
                case 15:
                    HDBOfficerEnquiryHandler.replyEnquiry(officer);
                    System.out.println("Press Enter to go back");
                    sc.nextLine();
                    break;
                case 16:
                    HDBOfficerApplicationController.updateApplication();
                    System.out.println("Press Enter to go back");
                    sc.nextLine();
                    break;
                case 17:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1-17.");
            }
        }
    }
}
