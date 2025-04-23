/**
 * This class provides the CLI menu interface for users logged in as applicants.
 * It allows applicants to interact with the system by:
 * <ul>
 *   <li>Changing their password</li>
 *   <li>Viewing and filtering open BTO projects</li>
 *   <li>Applying for a project and managing their application</li>
 *   <li>Booking a flat after successful applications</li>
 *   <li>Submitting, viewing, editing, and deleting enquiries</li>
 * </ul>
 *
 */

package boundary;

import control.*;
import entity.*;
import utils.ClearPage;
import utils.InputHelper;
import java.util.Scanner;
import java.util.List;
import entity.UserSession;
import entity.Project;


public class ApplicantMenu {
    private static final Scanner sc = new Scanner(System.in);
    private static final IApplicantEnquiryService enquiryService = new ApplicantEnquiryController();
    public static final IApplicantApplicationService applicationService = new ApplicantApplicationController();
    public static final IApplicantProjectService projectService = new ApplicantProjectController();
    /**
     * Launches the applicant menu interface in a loop until the user logs out.
     *
     * @param session The current user session containing the authenticated applicant.
     */
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
                    ChangeAccountPassword.changePassword(applicant);
                    break;
                case 2:
                    List<Project> allProjects = projectService.getOpenProjects(applicant);
                    ProjectFilterMenu.viewFilteredProjects(session, allProjects);
                    break;
                case 3:
                    ProjectFilterMenu.showFilterMenu(session);
                    break;
                case 4:
                    applicationService.applyForProject(applicant, projectService);
                    break;
                case 5:
                    applicationService.viewApplication(applicant);
                    break;
                case 6:
                    applicationService.requestBooking(applicant);
                    break;
                case 7:
                    applicationService.requestWithdrawal(applicant);
                    break;
                case 8:
                    enquiryService.submitEnquiry(applicant, projectService);
                    break;
                case 9:
                    enquiryService.viewEnquiries(applicant);
                    break;
                case 10:
                    enquiryService.editEnquiry(applicant);
                    break;
                case 11:
                    enquiryService.deleteEnquiry(applicant);
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