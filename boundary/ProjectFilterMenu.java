/**
 * This class provides the menu for a list of filters that the user can choose from for filtering of projects.
 * The class also handles printing of the filtered projects, using ProjectViewer methods
 */

package boundary;

import entity.*;
import utils.*;

import java.util.List;
import java.util.Scanner;

public class ProjectFilterMenu {

    /**
     * Prints the list of projects given filtered by the current filter settings
     * @param session The session of the authenticated user
     * @param allProjects The list of projects to be filtered
     */
    public static void viewFilteredProjects(UserSession session, List<Project> allProjects) {
        List<Project> filteredProjects = session.getProjectFilter().applyFilters(allProjects);

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match your current filters.");
            return;
        }

        System.out.println("Current filters:");
        printCurrentFilters(session.getProjectFilter());
        System.out.println();

        ProjectViewer.printProjects(filteredProjects, session.getUser());
    }

    /**
     * Prints the menu for the different filter settings
     * @param session Current session of the authenticated user
     */
    public static void showFilterMenu(UserSession session) {
        ProjectFilter filter = session.getProjectFilter();
        Scanner sc = new Scanner(System.in);

        while (true) {
            ClearPage.clearPage();

            System.out.println("""
            ╔════════════════════════════════════════════════════════════════════════╗
            ║                            PROJECT FILTERS                             ║
            ╠════════════════════════════════════════════════════════════════════════╣
            ║  1. Filter by Location                                                 ║
            ║  2. Filter by Flat Type                                                ║
            ║  3. Cycle Sort Order (A→Z / Z→A / None) (Repeat to cycle)              ║
            ║  4. Clear All Filters                                                  ║
            ║  5. Back to Main Menu                                                  ║
            ╚════════════════════════════════════════════════════════════════════════╝
            """);

            int choice = InputHelper.readInt("➤ Enter choice (1-5): ");

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter location (e.g., 'Yishun' or leave blank): ");
                    String loc = sc.nextLine().trim();
                    filter.setLocationFilter(loc.isEmpty() ? null : loc);
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter flat type (2-Room/3-Room or leave blank): ");
                        String type = sc.nextLine().trim();
                        filter.setFlatTypeFilter(type.isEmpty() ? null : type);
                        if ((filter.getFlatTypeFilter() == FlatType.THREEROOMS) && (session.getUser().getRole() == Role.APPLICANT)) {
                            System.out.println("Based on your status, you cannot view 3-Room flats. Please enter a different filter.");
                        } else break;
                    }
                }
                case 3 -> {
                    Boolean current = filter.getSortByAlphabetical();
                    Boolean newSort;
                    if (current == null) {
                        newSort = true; // None → A-Z
                    } else if (current) {
                        newSort = false; // A-Z → Z-A
                    } else {
                        newSort = null; // Z-A → None
                    }
                    filter.setSortByAlphabetical(newSort);
                    String sortDisplay = newSort == null ? "None" : newSort ? "A → Z" : "Z → A";
                    System.out.println("Sort order set to: " + sortDisplay);
                }
                case 4 -> {
                    filter.clearFilters();
                    System.out.println("All filters cleared");
                }
                case 5 -> {
                    return;
                }
            }

            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        }
    }

    /**
     * Prints out the current filter settings of the user
     * @param filter The current filter settings of the user
     */
    public static void printCurrentFilters(ProjectFilter filter) {
        System.out.println("Location: " +
                (filter.getLocationFilter() != null ? filter.getLocationFilter() : "None"));
        System.out.println("Flat Type: " +
                (filter.getFlatTypeFilter() != null ? filter.getFlatTypeFilter() : "None"));
        String sortDisplay = filter.getSortByAlphabetical() == null ? "None" :
                filter.getSortByAlphabetical() ? "A → Z" : "Z → A";
        System.out.println("Sort Order: " + sortDisplay);
    }
}
