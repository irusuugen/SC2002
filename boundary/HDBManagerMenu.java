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
            ║  5. View all projects                     ║
            ║  6. View all enquiries                    ║
            ║  7. Reply to enquiries                    ║
            ║  8. View officer registrations            ║
            ║  9. Process officer registrations         ║
            ║  10. Process applications                 ║
            ║  11. Process withdrawal requests          ║
            ║  12. Generate application report          ║
            ║  13. Logout                               ║
            ╚═══════════════════════════════════════════╝
            """);

            int choice;
            while (true) {
                choice = InputHelper.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 13)
                    break;
                System.out.println("Please enter a number between 1 and 13.");
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
                    HDBManagerProjectController.viewAllProjects(manager, allProjects);
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
        Scanner sc = new Scanner(System.in);
        
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
            
            int choice = InputHelper.readInt("➤ Enter choice (1-6): ");
            
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