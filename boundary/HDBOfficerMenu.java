package boundary;

import control.*;
import entity.*;
import repository.ProjectService;
import utils.ClearPage;
import utils.IntGetter;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

public class HDBOfficerMenu {
    private static final Scanner sc = new Scanner(System.in);

    public static void officerMenu(UserSession session) {
        HDBOfficer officer = (HDBOfficer) session.getUser();
        
        while (true) {
            ClearPage.clearPage();
            System.out.println("""
            ╔════════════════════════════════════════════╗
            ║               Officer Menu                 ║
            ╠════════════════════════════════════════════╣
            ║  1. Change password                        ║
            ║  2. View all open projects (Filtered)      ║
            ║  3. Set Project Filters                    ║
            ║  4. Apply for a project                    ║
            ║  5. View application                       ║
            ║  6. Book flat                              ║
            ║  7. Request withdrawal for application     ║
            ║  8. Submit enquiry about a project         ║
            ║  9. View enquiries                         ║
            ║ 10. Edit enquiries                         ║
            ║ 11. Delete enquiries                       ║
            ║ 12. Register to join a project             ║
            ║ 13. View registration                      ║
            ║ 14. View handling project                  ║
            ║ 15. View enquiries on handling project     ║
            ║ 16. Reply enquiries on handling project    ║
            ║ 17. Update successful application          ║
            ║ 18. Logout                                 ║
            ╚════════════════════════════════════════════╝
            """);

            int choice = IntGetter.readInt("➤ Enter your choice (1-18): ");
            ClearPage.clearPage();
                
            switch (choice) {
                case 1:
                    ChangeAccountPassword.changePassword(Role.HDB_OFFICER, officer);
                    break;
                case 2:
                    viewOpenProjectsWithFilters(session);
                    break;
                case 3:
                    showFilterMenu(session);
                    break;
                case 4:
                    ApplicantController.applyForProject(officer);
                    break;
                case 5:
                    ApplicantController.viewApplication(officer);
                    break;
                case 6:
                    ApplicantController.requestBooking(officer);
                    break;
                case 7:
                    ApplicantController.requestWithdrawal(officer);
                    break;
                case 8:
                    ApplicantController.submitEnquiry(officer);
                    break;
                case 9:
                    ApplicantController.viewEnquiries(officer);
                    break;
                case 10:
                    ApplicantController.editEnquiry(officer);
                    break;
                case 11:
                    ApplicantController.deleteEnquiry(officer);
                    break;
                case 12:
                    HDBOfficerRegistrationController.registerForProject(officer);
                    break;
                case 13:
                    HDBOfficerRegistrationController.viewRegistration(officer);
                    break;
                case 14:
                    ProjectViewer.printOneProject(officer.getAssignedProject(), officer);
                    break;
                case 15:
                    HDBOfficerEnquiryHandler.viewHandlingProjectEnquiries(officer);
                    break;
                case 16:
                    HDBOfficerEnquiryHandler.replyEnquiry(officer);
                    break;
                case 17:
                    HDBOfficerApplicationController.updateApplication(officer);
                    break;
                case 18:
                    System.out.println("Logging out...");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return;
            }
            System.out.println("➤ Press Enter to continue...");
            sc.nextLine(); 
        }
    }

    private static void viewOpenProjectsWithFilters(UserSession session) {
        HDBOfficer officer = (HDBOfficer) session.getUser();
        
        List<Project> projects = ProjectService.getAllProjects().stream()
            .filter(Project::isVisible)
            .collect(Collectors.toList());
        
        projects = session.getProjectFilter().applyFilters(projects);
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   AVAILABLE PROJECTS (FILTERED)");
        System.out.println("════════════════════════════════════════");
        printCurrentFilters(session.getProjectFilter());
        System.out.println("════════════════════════════════════════\n");
        
        if (projects.isEmpty()) {
            System.out.println("No projects match your current filters.");
            return;
        }
        
        System.out.printf("%-25s %-15s %-10s %-10s %-15s %-15s\n",
            "PROJECT", "LOCATION", "2-ROOM", "3-ROOM", "OPEN DATE", "CLOSE DATE");
        
        projects.forEach(p -> System.out.printf("%-25s %-15s %-10d %-10d %-15s %-15s\n",
            p.getProjectName(),
            p.getNeighborhood(),
            p.getNumFlatAvailable(FlatType.TWOROOMS),
            p.getNumFlatAvailable(FlatType.THREEROOMS),
            p.getOpenDate(),
            p.getCloseDate()
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
            ║  3. Toggle Sort Order                      ║
            ║  4. Clear All Filters                      ║
            ║  5. Back to Main Menu                      ║
            ╚════════════════════════════════════════════╝
            """);
            
            printCurrentFilters(filter);
            
            int choice = IntGetter.readInt("➤ Enter choice (1-5): ");
            
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
                    boolean newSort = !filter.isSortByAlphabetical();
                    filter.setSortByAlphabetical(newSort);
                    System.out.println("Sort order set to: " + (newSort ? "A-Z" : "Z-A"));
                    break;
                case 4:
                    filter.clearFilters();
                    System.out.println("All filters cleared");
                    break;
                case 5:
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
        System.out.println("🔃 Sort Order: " + 
            (filter.isSortByAlphabetical() ? "A → Z" : "Z → A"));
    }
}