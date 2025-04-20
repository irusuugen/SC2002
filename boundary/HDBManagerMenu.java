package boundary;

import java.util.*;
import entity.*;
import repository.ProjectService;
import control.*;
import utils.*;

public class HDBManagerMenu {
    public static void managerMenu(UserSession session) {
        HDBManager manager = (HDBManager) session.getUser();
        Scanner sc = new Scanner(System.in);
        List<Project> allProjects = ProjectService.getAllProjects();
        List<Application> allApplications = HDBManagerApplicationController.getAllApplications(manager);

        while (true) {
            ClearPage.clearPage();
            System.out.println("""
            ╔═══════════════════════════════════════════╗
            ║             HDB Manager Menu              ║
            ╠═══════════════════════════════════════════╣
            ║  1. Create new project                    ║
            ║  2. Edit existing project                 ║
            ║  3. Delete existing project               ║
            ║  4. Toggle project visibility             ║
            ║  5. View all projects (filtered)          ║
            ║  6. View all enquiries                    ║
            ║  7. Reply to enquiries                    ║
            ║  8. View officer registrations            ║
            ║  9. Process officer registrations         ║
            ║  10. Process applications                 ║
            ║  11. Process withdrawal requests          ║
            ║  12. Generate application report          ║
            ║  13. Set project filters                  ║
            ║  14. Logout                               ║
            ╚═══════════════════════════════════════════╝
            """);

            int choice;
            while (true) {
                choice = InputHelper.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 14)
                    break;
                System.out.println("Please enter a number between 1 and 14.");
            }
            ClearPage.clearPage();
            
            switch (choice) {
                case 1:
                    // Create new project
                    HDBManagerProjectController.createProject(manager, allProjects);
                    break;
                case 2:
                    // Edit existing project
                    HDBManagerProjectController.editProject(manager);
                    break;
                case 3:
                    // Delete existing project
                    HDBManagerProjectController.deleteProject(manager, allProjects);
                    break;
                case 4:
                    // Toggle project visibility
                    HDBManagerProjectController.toggleProjectVisibility(manager);
                    break;
                case 5:
                    // View all projects
                    HDBManagerProjectController.viewAllProjects(manager, allProjects, session);
                    break;
                case 6:
                    // View all enquiries
                    HDBManagerEnquiryController.viewAllEnquiries(allProjects);
                    break;
                case 7:
                    HDBManagerEnquiryController.replyEnquiry(manager);
                    break;
                case 8:
                    HDBManagerRegistrationController.viewRegistrations(manager);
                    break;
                case 9:
                    HDBManagerRegistrationController.processRegistrations(manager);
                    break;
                case 10:
                    HDBManagerApplicationController.processApplication(manager);
                    break;
                case 11:
                    HDBManagerApplicationController.processWithdrawal(manager);
                    break;
                case 12:
                    HDBManagerReportPrintController.generateAndPrintReport(allApplications);
                    break;
                case 13:
                    ProjectFilterMenu.showFilterMenu(session);
                    break;
                case 14:
                    System.out.println("Logging out...");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return;
            }

            System.out.println("➤ Press Enter to continue...");
            sc.nextLine();
        }
    }
}