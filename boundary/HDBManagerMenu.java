/**
 * This class provides the CLI menu interface for users logged in as managers.
 * It allows managers to interact with the system by:
 * <ul>
 *   <li>Changing their password</li>
 *   <li>Viewing and filtering all BTO projects</li>
 *   <li>Managing officer registrations</li>
 *   <li>Managing applicant applications and withdrawal requests</li>
 *   <li>Viewing and replying enquiries</li>
 * </ul>
 *
 */

package boundary;

import java.util.*;
import entity.*;
import repository.ProjectService;
import control.*;
import utils.*;

public class HDBManagerMenu {
    private static final IManagerEnquiryService enquiryService = new HDBManagerEnquiryController();
    private static final IManagerProjectService projectService = new HDBManagerProjectController();
    private static final IManagerRegistrationService registrationService = new HDBManagerRegistrationController();
    private static final IManagerApplicationService applicationService = new HDBManagerApplicationController();

    /**
     * Launches the manager menu interface in a loop until the user logs out.
     *
     * @param session The current user session containing the authenticated applicant.
     */
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
            ║  1. Change password                       ║
            ║  2. Create new project                    ║
            ║  3. Edit existing project                 ║
            ║  4. Delete existing project               ║
            ║  5. Toggle project visibility             ║
            ║  6. View all projects (filtered)          ║
            ║  7. View all enquiries                    ║
            ║  8. Reply to enquiries                    ║
            ║  9. View officer registrations            ║
            ║  10. Process officer registrations        ║
            ║  11. Process applications                 ║
            ║  12. Process withdrawal requests          ║
            ║  13. Generate application report          ║
            ║  14. Set project filters                  ║
            ║  15. Logout                               ║
            ╚═══════════════════════════════════════════╝
            """);

            int choice;
            while (true) {
                choice = InputHelper.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 15)
                    break;
                System.out.println("Please enter a number between 1 and 15.");
            }
            ClearPage.clearPage();
            
            switch (choice) {
                case 1:
                    // Change password
                    ChangeAccountPassword.changePassword(manager);
                    break;
                case 2:
                    // Create new project
                    projectService.createProject(manager);
                    break;
                case 3:
                    // Edit existing project
                    projectService.editProject(manager);
                    break;
                case 4:
                    // Delete existing project
                    projectService.deleteProject(manager, allProjects);
                    break;
                case 5:
                    // Toggle project visibility
                    projectService.toggleProjectVisibility(manager);
                    break;
                case 6:
                    // View all projects
                    projectService.viewAllProjects(manager, allProjects, session);
                    break;
                case 7:
                    // View all enquiries
                    enquiryService.viewAllEnquiries(allProjects);
                    break;
                case 8:
                    Enquiry enquiry = enquiryService.selectEnquiry(manager, projectService);
                    enquiryService.replyEnquiry(enquiry);
                    break;
                case 9:
                    registrationService.viewRegistrations(manager);
                    break;
                case 10:
                    registrationService.processRegistrations(manager);
                    break;
                case 11:
                    applicationService.processApplication(manager);
                    break;
                case 12:
                    applicationService.processWithdrawal(manager);
                    break;
                case 13:
                    HDBManagerReportPrintController.generateAndPrintReport(allApplications);
                    break;
                case 14:
                    ProjectFilterMenu.showFilterMenu(session);
                    break;
                case 15:
                    System.out.println("Logging out...");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return;
            }

            System.out.println("➤ Press Enter to continue...");
            sc.nextLine();
        }
    }
}