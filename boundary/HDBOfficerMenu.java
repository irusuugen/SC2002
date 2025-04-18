package boundary;

import control.*;
import entity.*;
import java.util.Scanner;
import utils.*;

public class HDBOfficerMenu {
    public static void officerMenu(HDBOfficer officer) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {  // Main menu loop
            ClearPage.clearPage();
            System.out.println("""
            ╔════════════════════════════════════════════════════════╗
            ║                    Officer Menu                        ║
            ╠════════════════════════════════════════════════════════╣
            ║  1.  Change password                                   ║
            ║  2.  View all open projects                            ║
            ║  3.  Apply for a project                               ║
            ║  4.  View application                                  ║
            ║  5.  Book flat                                         ║
            ║  6.  Request withdrawal for application                ║
            ║  7.  Submit enquiry about a project                    ║
            ║  8.  View enquiries                                    ║
            ║  9.  Edit enquiries                                    ║
            ║ 10.  Delete enquiries                                  ║
            ║ 11.  Register to join a project                        ║
            ║ 12.  View registrations                                ║
            ║ 13.  View assigned projects                            ║
            ║ 14.  View enquiries on assigned projects               ║
            ║ 15.  Reply to enquiries on assigned projects           ║
            ║ 16.  Update successful application                     ║
            ║ 17.  Logout                                            ║
            ╚════════════════════════════════════════════════════════╝
            """);

            // Obtaining user's choice
            int choice;
            while (true) {
                choice = IntGetter.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 17) break;
                System.out.println("Please enter a number between 1 and 17.");
            }
            ClearPage.clearPage(); // Clears terminal for the page for the corresponding options
                
            switch (choice) {
                case 1:
                	ChangeAccountPassword.changePassword(Role.HDB_OFFICER,officer);
                    break;
                case 2:
                    // View projects
                    ApplicantController.viewOpenProjects(officer);
                    break;
                case 3:
                    // Apply for project
                    ApplicantController.applyForProject(officer);
                    break;
                case 4:
                    // View application
                    ApplicantController.viewApplication(officer);
                    break;
                case 5:
                    // Book with officer
                    ApplicantController.requestBooking(officer);
                    break;
                case 6:
                    // Request withdrawal
                    ApplicantController.requestWithdrawal(officer);
                    break;
                case 7:
                    // Submit enquiry
                    ApplicantController.submitEnquiry(officer);
                    break;
                case 8:
                    // View enquiry
                    ApplicantController.viewEnquiries(officer);
                    break;
                case 9:
                    // Edit enquiry
                    ApplicantController.editEnquiry(officer);
                    break;
                case 10:
                    // Delete enquiry
                    ApplicantController.deleteEnquiry(officer);
                    break;
                case 11:
                    // Register for a project
                	HDBOfficerRegistrationController.registerForProject(officer);
                    break;
                case 12:
                    // View registered projects
                	HDBOfficerRegistrationController.viewRegistrations(officer);
                    break;
                case 13:
                    // View assigned projects
                	ProjectViewer.printProjects(officer.getAssignedProjects(), officer);
                    break;
                case 14:
                    // View enquiries for assigned projects
                	HDBOfficerEnquiryHandler.viewAssignedProjectsEnquiries(officer);
                    break;
                case 15:
                    // Reply to enquiries for assigned projects
                	HDBOfficerEnquiryHandler.replyEnquiry(officer);
                    break;
                case 16:
                    // Update successful applications
                	HDBOfficerApplicationController.updateApplication(officer);
                    break;
                case 17:
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
