/**
 * This class provides the CLI menu interface for users logged in as officers.
 * It allows managers to interact with the system by:
 * <ul>
 *   <li>Changing their password</li>
 *   <li>Performing applicant operations</li>
 *   <li>Viewing and filtering all BTO projects</li>
 *   <li>Managing applicant bookings</li>
 *   <li>Viewing and replying enquiries</li>
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

public class HDBOfficerMenu {
    private static final IApplicantEnquiryService applicantEnquiryService = new ApplicantEnquiryController();
    private static final IApplicantApplicationService applicantApplicationService = new ApplicantApplicationController();
    private static final IApplicantProjectService projectService = new ApplicantProjectController();
    private static final IOfficerEnquiryService officerEnquiryService = new HDBOfficerEnquiryController();
    private static final IOfficerApplicationService officerApplicationService = new HDBOfficerApplicationController();
    private static final IOfficerRegistrationService officerRegistrationService = new HDBOfficerRegistrationController();

    /**
     * Launches the manager menu interface in a loop until the user logs out.
     *
     * @param session The current user session containing the authenticated officer
     */
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
            ║  13.  View assigned project                            ║
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
                    ChangeAccountPassword.changePassword(officer);
                    break;
                case 2:
                    List<Project> openProjects = projectService.getOpenProjects(officer);
                    ProjectFilterMenu.viewFilteredProjects(session, openProjects);
                    break;
                case 3:
                    applicantApplicationService.applyForProject(officer, projectService);
                    break;
                case 4:
                    applicantApplicationService.viewApplication(officer);
                    break;
                case 5:
                    applicantApplicationService.requestBooking(officer);
                    break;
                case 6:
                    applicantApplicationService.requestWithdrawal(officer);
                    break;
                case 7:
                    applicantEnquiryService.submitEnquiry(officer, projectService);
                    break;
                case 8:
                    applicantEnquiryService.viewEnquiries(officer);
                    break;
                case 9:
                    applicantEnquiryService.editEnquiry(officer);
                    break;
                case 10:
                    applicantEnquiryService.deleteEnquiry(officer);
                    break;
                case 11:
                    // Register for a project
                    officerRegistrationService.registerForProject(officer);
                    break;
                case 12:
                    // View registered projects
                    officerRegistrationService.viewRegistrations(officer);
                    break;
                case 13:
                    // View assigned project
                    officerRegistrationService.printAssignedProject(officer);
                    break;
                case 14:
                    // View enquiries for assigned projects
                    officerEnquiryService.viewEnquiries(officer);
                    break;
                case 15:
                    // Reply to enquiries for assigned projects
                    Enquiry enquiry = officerEnquiryService.selectAssignedProjectEnquiry(officer);
                    officerEnquiryService.replyEnquiry(enquiry);
                    break;
                case 16:
                    // Update successful applications
                    officerApplicationService.updateApplication(officer);
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