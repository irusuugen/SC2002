package boundary;

import control.*;
import entity.*;
import utils.ClearPage;
import utils.InputHelper;
import java.util.Scanner;
import java.util.List;

public class HDBOfficerMenu {
    public static void officerMenu(UserSession session) {
        HDBOfficer officer = (HDBOfficer) session.getUser();
        Scanner sc = new Scanner(System.in);

        while (true) {
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
            ║  10.  Delete enquiries                                 ║
            ║  11.  Register to join a project                       ║
            ║  12.  View registrations                               ║
            ║  13.  View assigned projects                           ║
            ║  14.  View enquiries on assigned projects              ║
            ║  15.  Reply to enquiries on assigned projects          ║
            ║  16.  Update successful application                    ║
            ║  17.  Logout                                           ║
            ║  18.  Set project filters                              ║
            ╚════════════════════════════════════════════════════════╝
            """);

            // Obtaining user's choice
            int choice;
            while (true) {
                choice = InputHelper.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 18) break;
                System.out.println("Please enter a number between 1 and 18.");
            }
            ClearPage.clearPage(); // Clears terminal for the page for the corresponding options

            switch (choice) {
                case 1:
                    ChangeAccountPassword.changePassword(Role.HDB_OFFICER, officer);
                    break;
                case 2:
                    List<Project> openProjects = ApplicantController.getOpenProjects(officer);
                    ProjectFilterMenu.viewFilteredProjects(session, openProjects);
                    break;
                case 3:
                    ApplicantController.applyForProject(officer);
                    break;
                case 4:
                    ApplicantController.viewApplication(officer);
                    break;
                case 5:
                    ApplicantController.requestBooking(officer);
                    break;
                case 6:
                    ApplicantController.requestWithdrawal(officer);
                    break;
                case 7:
                    ApplicantController.submitEnquiry(officer);
                    break;
                case 8:
                    ApplicantController.viewEnquiries(officer);
                    break;
                case 9:
                    ApplicantController.editEnquiry(officer);
                    break;
                case 10:
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
                    System.out.println("Logging out...");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return;
                case 18:
                    ProjectFilterMenu.showFilterMenu(session);
            }
            System.out.println("➤ Press Enter to continue...");
            sc.nextLine();
        }
    }

}