package boundary;

import control.ApplicantController;
import entity.*;
import utils.ClearPage;
import utils.InputHelper;
import java.util.Scanner;
import java.util.List;
import entity.UserSession;
import entity.Project;


public class ApplicantMenu {
    private static final Scanner sc = new Scanner(System.in);

    public static void applicantMenu(UserSession session) {
        Applicant applicant = (Applicant) session.getUser();
        
        while (true) {
            ClearPage.clearPage();
            System.out.println("""
            ╔════════════════════════════════════════════╗
            ║              Applicant Menu                ║
            ╠════════════════════════════════════════════╣
            ║  1. Change password                        ║
            ║  2. View all open projects (filtered)      ║
            ║  3. Set project filters                    ║
            ║  4. Apply for a project                    ║
            ║  5. View application                       ║
            ║  6. Book flat                              ║
            ║  7. Request withdrawal for application     ║
            ║  8. Submit enquiry about a project         ║
            ║  9. View enquiries                         ║
            ║ 10. Edit enquiries                         ║
            ║ 11. Delete enquiries                       ║
            ║ 12. Logout                                 ║
            ╚════════════════════════════════════════════╝
            """);

            // Obtaining user's choice
            int choice;
            while (true) {
                choice = InputHelper.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 12) break;
                System.out.println("Please enter a number between 1 and 12.");
            }
            ClearPage.clearPage(); // Clears terminal for the page for the corresponding options
                
            switch (choice) {
                case 1:
                    ApplicantController.changePassword(applicant);
                    break;
                case 2:
                    List<Project> allProjects = ApplicantController.getOpenProjects(applicant); // Assuming this returns open projects only
                    ProjectFilterMenu.viewFilteredProjects(session, allProjects);
                    break;
                case 3:
                    ProjectFilterMenu.showFilterMenu(session);
                    break;
                case 4:
                    ApplicantController.applyForProject(applicant);
                    break;
                case 5:
                    ApplicantController.viewApplication(applicant);
                    break;
                case 6:
                    ApplicantController.requestBooking(applicant);
                    break;
                case 7:
                    ApplicantController.requestWithdrawal(applicant);
                    break;
                case 8:
                    ApplicantController.submitEnquiry(applicant);
                    break;
                case 9:
                    ApplicantController.viewEnquiries(applicant);
                    break;
                case 10:
                    ApplicantController.editEnquiry(applicant);
                    break;
                case 11:
                    ApplicantController.deleteEnquiry(applicant);
                    break;
                case 12:
                    System.out.println("Logging out...");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return;
            }
            System.out.println("➤ Press Enter to continue...");
            sc.nextLine(); 
        }
    }
}