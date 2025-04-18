package boundary;

import java.util.*;
import entity.*;
import control.*;
import utils.*;

public class HDBManagerMenu {
    public static void managerMenu(HDBManager manager, List<Project> allProjects, List<Application> allApplications) {
        Scanner sc = new Scanner(System.in);
        HDBManagerProjectController projectController = new HDBManagerProjectController();
        HDBManagerRegistrationController registrationController = new HDBManagerRegistrationController();
        HDBManagerApplicationController applicationController = new HDBManagerApplicationController();
        HDBManagerEnquiryController enquiryController = new HDBManagerEnquiryController();
        HDBManagerReportPrintController reportController = new HDBManagerReportPrintController();

        while (true) {
            ClearPage.clearPage();
            System.out.println("""
                    ╔═══════════════════════════════════════════╗
                    ║              HDB Manager Menu             ║
                    ╠═══════════════════════════════════════════╣
                    ║  1. Create new project                    ║
                    ║  2. Toggle project visibility             ║
                    ║  3. View all enquiries                    ║
                    ║  4. Reply to enquiry                      ║
                    ║  5. Approve officer registration          ║
                    ║  6. Reject officer registration           ║
                    ║  7. Approve application                   ║
                    ║  8. Reject application                    ║
                    ║  9. Approve withdrawal                    ║
                    ║ 10. Generate application report           ║
                    ║ 11. Logout                                ║
                    ╚═══════════════════════════════════════════╝
                    """);

            int choice;
            while (true) {
                choice = IntGetter.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 11)
                    break;
                System.out.println("Please enter a number between 1 and 11.");
            }
            ClearPage.clearPage();
            
            switch (choice) {
                case 1:
                    projectController.createProject(manager, allProjects);
                    break;
                case 2:
                    projectController.toggleProjectVisibility(manager);
                    break;
                case 3:
                    enquiryController.viewAllEnquiries(allProjects);
                    break;
                case 4:
                    enquiryController.replyToEnquiry(allProjects);
                    break;
                case 5:
                    registrationController.handleRegistration(manager, true);
                    break;
                case 6:
                    registrationController.handleRegistration(manager, false);
                    break;
                case 7:
                    applicationController.processApplication(allApplications, true);
                    break;
                case 8:
                    applicationController.processApplication(allApplications, false);
                    break;
                case 9:
                    registrationController.approveWithdrawal(allApplications);
                    break;
                case 10:
                    reportController.generateAndPrintReport(allApplications);
                    break;
                case 11:
                    System.out.println("Logging out...");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println("➤ Press Enter to go back.");
            sc.nextLine();
        }
    }
}
