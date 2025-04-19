package boundary;

import java.util.*;
import entity.*;
import repository.ProjectService;
import control.*;
import utils.*;

public class HDBManagerMenu {
    private static final Scanner sc = new Scanner(System.in);

    public static void managerMenu(UserSession session) {
        HDBManager manager = (HDBManager) session.getUser();
        HDBManagerProjectController projectController = new HDBManagerProjectController();
        HDBManagerRegistrationController registrationController = new HDBManagerRegistrationController();
        HDBManagerApplicationController applicationController = new HDBManagerApplicationController();
        HDBManagerEnquiryController enquiryController = new HDBManagerEnquiryController();
        HDBManagerReportPrintController reportController = new HDBManagerReportPrintController();

        List<Project> allProjects = ProjectService.getAllProjects();
        List<Application> allApplications = HDBManagerApplicationController.getAllApplications(manager);

        while (true) {
            ClearPage.clearPage();
            System.out.println("""
                    ╔═══════════════════════════════════════════╗
                    ║              HDB Manager Menu             ║
                    ╠═══════════════════════════════════════════╣
                    ║  1. Create new project                    ║
                    ║  2. Toggle project visibility             ║
                    ║  3. View all projects (Filtered)          ║
                    ║  4. Set Project Filters                   ║
                    ║  5. View all enquiries                    ║
                    ║  6. Reply to enquiry                      ║
                    ║  7. Approve officer registration          ║
                    ║  8. Reject officer registration           ║
                    ║  9. Approve application                   ║
                    ║ 10. Reject application                    ║
                    ║ 11. Approve withdrawal                    ║
                    ║ 12. Generate application report           ║
                    ║ 13. Logout                                ║
                    ╚═══════════════════════════════════════════╝
                    """);

            int choice = IntGetter.readInt("➤ Enter your choice (1-13): ");
            ClearPage.clearPage();
            
            switch (choice) {
                case 1:
                    projectController.createProject(manager, allProjects);
                    break;
                case 2:
                    projectController.toggleProjectVisibility(manager);
                    break;
                case 3:
                    viewProjectsWithFilters(session, allProjects);
                    break;
                case 4:
                    showFilterMenu(session);
                    break;
                case 5:
                    enquiryController.viewAllEnquiries(allProjects);
                    break;
                case 6:
                    enquiryController.replyToEnquiry(allProjects);
                    break;
                case 7:
                    registrationController.handleRegistration(manager, true);
                    break;
                case 8:
                    registrationController.handleRegistration(manager, false);
                    break;
                case 9:
                    applicationController.processApplication(allApplications, true);
                    break;
                case 10:
                    applicationController.processApplication(allApplications, false);
                    break;
                case 11:
                    registrationController.approveWithdrawal(allApplications);
                    break;
                case 12:
                    reportController.generateAndPrintReport(allApplications);
                    break;
                case 13:
                    System.out.println("Logging out...");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return;
            }

            System.out.println("➤ Press Enter to continue...");
            sc.nextLine();
        }
    }

    private static void viewProjectsWithFilters(UserSession session, List<Project> allProjects) {
        List<Project> projects = session.getProjectFilter().applyFilters(allProjects);
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   ALL PROJECTS (FILTERED)");
        System.out.println("════════════════════════════════════════");
        printCurrentFilters(session.getProjectFilter());
        System.out.println("════════════════════════════════════════\n");
        
        if (projects.isEmpty()) {
            System.out.println("No projects match your current filters.");
            return;
        }
        
        System.out.printf("%-25s %-15s %-10s %-10s %-15s %-15s %-10s\n",
            "PROJECT", "LOCATION", "2-ROOM", "3-ROOM", "OPEN DATE", "CLOSE DATE", "VISIBLE");
        
        projects.forEach(p -> System.out.printf("%-25s %-15s %-10d %-10d %-15s %-15s %-10s\n",
            p.getProjectName(),
            p.getNeighborhood(),
            p.getNumFlatAvailable(FlatType.TWOROOMS),
            p.getNumFlatAvailable(FlatType.THREEROOMS),
            p.getOpenDate(),
            p.getCloseDate(),
            p.isVisible() ? "Yes" : "No"
        ));
    }

    private static void showFilterMenu(UserSession session) {
        ProjectFilter filter = session.getProjectFilter();
        
        while (true) {
            ClearPage.clearPage();
            System.out.println("""
            ╔════════════════════════════════════════════╗
            ║            PROJECT FILTERS                 ║
            ╠════════════════════════════════════════════╣
            ║  1. Filter by Location                     ║
            ║  2. Filter by Flat Type                    ║
            ║  3. Filter by Visibility                   ║
            ║  4. Toggle Sort Order                      ║
            ║  5. Clear All Filters                      ║
            ║  6. Back to Main Menu                      ║
            ╚════════════════════════════════════════════╝
            """);
            
            printCurrentFilters(filter);
            
            int choice = IntGetter.readInt("➤ Enter choice (1-6): ");
            
            switch (choice) {
                case 1:
                    System.out.print("Enter location (e.g., 'Yishun' or leave blank): ");
                    String loc = sc.nextLine().trim();
                    filter.setLocationFilter(loc.isEmpty() ? null : loc);
                    break;
                case 2:
                    System.out.print("Enter flat type (2-Room/3-Room or leave blank): ");
                    String type = sc.nextLine().trim();
                    filter.setFlatTypeFilter(type.isEmpty() ? null : type);
                    break;
                case 3:
                    System.out.print("Filter visible projects? (Y/N/Any): ");
                    String vis = sc.nextLine().trim();
                    if (vis.equalsIgnoreCase("Y")) {
                        filter.setVisibilityFilter(true);
                    } else if (vis.equalsIgnoreCase("N")) {
                        filter.setVisibilityFilter(false);
                    } else {
                        filter.setVisibilityFilter(null);
                    }
                    break;
                case 4:
                    boolean newSort = !filter.isSortByAlphabetical();
                    filter.setSortByAlphabetical(newSort);
                    System.out.println("Sort order set to: " + (newSort ? "A-Z" : "Z-A"));
                    break;
                case 5:
                    filter.clearFilters();
                    System.out.println("All filters cleared");
                    break;
                case 6:
                    return;
            }
        }
    }

    private static void printCurrentFilters(ProjectFilter filter) {
        System.out.println("\nCurrent Filters:");
        System.out.println("📍 Location: " + 
            (filter.getLocationFilter() != null ? filter.getLocationFilter() : "None"));
        System.out.println("🏠 Flat Type: " + 
            (filter.getFlatTypeFilter() != null ? 
                (filter.getFlatTypeFilter() == FlatType.TWOROOMS ? "2-Room" : "3-Room") 
                : "None"));
        System.out.println("👁️ Visibility: " +
            (filter.getVisibilityFilter() != null ?
                (filter.getVisibilityFilter() ? "Visible Only" : "Hidden Only")
                : "Any"));
        System.out.println("🔃 Sort Order: " + 
            (filter.isSortByAlphabetical() ? "A → Z" : "Z → A"));
    }
}