/* Class for printing of project(s) */

package boundary;

import entity.*;
import java.util.List;

public class ProjectViewer {
    private static final int BOX_WIDTH = 74;

    public static void printProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            System.out.println("No projects to display.");
            return;
        }
        System.out.println("Here is the list of available projects:");
        printTopBorder();
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);

            printRow("Project Name", project.getProjectName());
            printRow("Neighborhood", project.getNeighborhood());
            printRow("Units for 2-Room Flats", String.valueOf(project.getNumFlatAvailable(FlatType.TWOROOMS)));
            printRow("Price for 2-Room Flats", String.format("$%.2f", project.getSellingPrice(FlatType.TWOROOMS)));
            printRow("Units for 3-Room Flats", String.valueOf(project.getNumFlatAvailable(FlatType.THREEROOMS)));
            printRow("Price for 3-Room Flats", String.format("$%.2f", project.getSellingPrice(FlatType.THREEROOMS)));
            printRow("Application Period", project.getOpenDate() + " to " + project.getCloseDate());
            
            if (i < projects.size() - 1) {
                printDivider(); // Inner separator
            } else {
                printBottomBorder(); // End of final project
            }
        }
    }

    private static void printTopBorder() {
        System.out.println("╔" + "═".repeat(BOX_WIDTH - 2) + "╗");
    }

    private static void printBottomBorder() {
        System.out.println("╚" + "═".repeat(BOX_WIDTH - 2) + "╝");
    }

    private static void printDivider() {
        System.out.println("╠" + "═".repeat(BOX_WIDTH - 2) + "╣");
    }

    private static void printRow(String label, String value) {
        System.out.printf("║ %-25s | %-42s ║\n", label, value);
    }
}