package boundary;

import control.*;
import entity.*;
import repository.ProjectService;
import utils.ClearPage;
import utils.InputHelper;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

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
            ║ 10.  Delete enquiries                                  ║
            ║ 11.  Register to join a project                        ║
            ║ 12.  View registrations                                ║
            ║ 13.  View assigned projects                            ║
            ║ 14.  View enquiries on assigned projects               ║
            ║ 15.  Reply to enquiries on assigned projects           ║
            ║ 16.  Update successful application                     ║
            ║ 17.  Logout                                            ║
            ╚════════════════════════════════════════════════════════╝
            """);

            // Obtaining user's choice
            int choice;
            while (true) {
                choice = InputHelper.readInt("➤ Enter your choice: ");
                if (choice >= 1 && choice <= 17) break;
                System.out.println("Please enter a number between 1 and 17.");
            }
            ClearPage.clearPage(); // Clears terminal for the page for the corresponding options
                
            switch (choice) {
                case 1:
                    ChangeAccountPassword.changePassword(Role.HDB_OFFICER, officer);
                    break;
                case 2:
                    viewOpenProjectsWithFilters(session);
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
        Scanner sc = new Scanner(System.in);
        
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
            
            int choice = InputHelper.readInt("➤ Enter choice (1-5): ");
            
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