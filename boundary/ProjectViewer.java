/* Class for printing of project(s) */

package boundary;

import entity.*;
import java.util.List;
import utils.*;

public class ProjectViewer {
    public static void printOneProject(Project project, Applicant applicant) {
        System.out.println("Here is the project information:");
        BoxPrinter.printTopBorder();
        filterProjectDetailsforApplicants(project, applicant);
        BoxPrinter.printBottomBorder();
    }


    public static void printProjects(List<Project> projects, Applicant applicant) {
        if (projects.isEmpty()) {
            System.out.println("No projects available to display.");
            return;
        }
        System.out.println("Here is the list of available projects:");
        BoxPrinter.printTopBorder();
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            filterProjectDetailsforApplicants(project, applicant);
            if (i < projects.size() - 1) {
                BoxPrinter.printDivider(); // Inner separator
            } else {
                BoxPrinter.printBottomBorder(); // End of final project
            }
        }
    }

    public static void filterProjectDetailsforApplicants(Project project, Applicant applicant) {
        BoxPrinter.printRow("Project Name", project.getProjectName());
        BoxPrinter.printRow("Neighborhood", project.getNeighborhood());
        BoxPrinter.printRow("Units for 2-Room Flats", String.valueOf(project.getNumFlatAvailable(FlatType.TWOROOMS)));
        BoxPrinter.printRow("Price for 2-Room Flats", String.format("$%.2f", project.getSellingPrice(FlatType.TWOROOMS)));
        if (applicant.getUserGroup() == UserGroup.MARRIED) {
            BoxPrinter.printRow("Units for 3-Room Flats", String.valueOf(project.getNumFlatAvailable(FlatType.THREEROOMS)));
            BoxPrinter.printRow("Price for 3-Room Flats", String.format("$%.2f", project.getSellingPrice(FlatType.THREEROOMS)));
        }
        BoxPrinter.printRow("Application Period", project.getOpenDate() + " to " + project.getCloseDate());
    }
}