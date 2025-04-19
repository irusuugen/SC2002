package boundary;

import entity.*;
import utils.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProjectFilterMenu {
    public static void viewFilteredProjects(UserSession session, List<Project> allProjects) {
        List<Project> filteredProjects = applyFilters(session.getProjectFilter(), allProjects);

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match your current filters.");
            return;
        }

        System.out.println("Current filters: ");
        printCurrentFilters(session.getProjectFilter());
        System.out.println();

        // Use ProjectViewer to display the filtered projects
        ProjectViewer.printProjects(filteredProjects, session.getUser());
    }

    private static List<Project> applyFilters(ProjectFilter filter, List<Project> allProjects) {
        return allProjects.stream()
                .filter(p -> filterLocation(p, filter.getLocationFilter()))
                .filter(p -> filterFlatType(p, filter.getFlatTypeFilter()))
                .sorted(filter.isSortByAlphabetical() ?
                        (p1, p2) -> p1.getProjectName().compareToIgnoreCase(p2.getProjectName()) :
                        (p1, p2) -> p2.getProjectName().compareToIgnoreCase(p1.getProjectName()))
                .collect(Collectors.toList());
    }

    private static boolean filterLocation(Project project, String locationFilter) {
        return locationFilter == null || project.getNeighborhood().equalsIgnoreCase(locationFilter);
    }

    private static boolean filterFlatType(Project project, FlatType flatTypeFilter) {
        if (flatTypeFilter == null) return true;

        return project.getNumFlatAvailable(flatTypeFilter) > 0;
    }

    public static void showFilterMenu(UserSession session) {
        ProjectFilter filter = session.getProjectFilter();
        Scanner sc = new Scanner(System.in);

        while (true) {
            ClearPage.clearPage();

            BoxPrinter.printTopBorder();
            System.out.println(BoxPrinter.centerInBox("            PROJECT FILTERS"));
            BoxPrinter.printBottomBorder();

            printCurrentFilters(filter);

            System.out.println("""
            ╠════════════════════════════════════════════╣
            ║  1. Filter by Location                     ║
            ║  2. Filter by Flat Type                    ║
            ║  3. Toggle Sort Order                      ║
            ║  4. Clear All Filters                      ║
            ║  5. Back to Main Menu                      ║
            ╚════════════════════════════════════════════╝
            """);

            int choice = InputHelper.readInt("➤ Enter choice (1-5): ");

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter location (e.g., 'Yishun' or leave blank): ");
                    String loc = sc.nextLine().trim();
                    filter.setLocationFilter(loc.isEmpty() ? null : loc);
                }
                case 2 -> {
                    System.out.print("Enter flat type (2-Room/3-Room or leave blank): ");
                    String type = sc.nextLine().trim();
                    filter.setFlatTypeFilter(type.isEmpty() ? null : type);
                }
                case 3 -> {
                    boolean newSort = !filter.isSortByAlphabetical();
                    filter.setSortByAlphabetical(newSort);
                    System.out.println("Sort order set to: " + (newSort ? "A-Z" : "Z-A"));
                }
                case 4 -> {
                    filter.clearFilters();
                    System.out.println("All filters cleared");
                }
                case 5 -> { return; }
            }
        }
    }

    public static void printCurrentFilters(ProjectFilter filter) {
        System.out.println("Location: " +
                (filter.getLocationFilter() != null ? filter.getLocationFilter() : "None"));
        System.out.println("Flat Type: " +
                (filter.getFlatTypeFilter() != null ? filter.getFlatTypeFilter() : "None"));
        System.out.println("Sort Order: " +
                (filter.isSortByAlphabetical() ? "A → Z" : "Z → A"));
    }
}
