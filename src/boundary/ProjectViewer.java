/**
 * This class is responsible for printing project information in a formatted box layout.
 * Either the full list of projects or a singular project can be printed, and project details are filtered out
 * based on what information each user role needs.
 */

package boundary;

import entity.*;
import java.util.List;
import utils.*;

public class ProjectViewer {

    /**
     *
     * @param project Project whose information is being printed
     * @param role The role of the user who is viewing the project details
     * @param user The user who is viewing the project details
     */
    public static void printOneProject(Project project, Role role, User user) {
        System.out.println("Here is the project information:");
        BoxPrinter.printTopBorder();

        if (role == Role.HDB_OFFICER) {
            showFullProjectDetails(project);
        }
        else if (role == Role.APPLICANT) {
            filterProjectDetailsForApplicants(project, (Applicant) user);
        } else if (role == Role.HDB_MANAGER) {
            showFullProjectDetails(project);
        }

        BoxPrinter.printBottomBorder();
    }

    /**
     * Prints the list of projects
     * @param projects The list of projects to be printed
     * @param user The user viewing the list of projects
     */
    public static void printProjects(List<Project> projects, User user) {
        if (projects.isEmpty()) {
            System.out.println("No projects available to display.");
            return;
        }

        System.out.println("Here is the list of available projects:");
        BoxPrinter.printTopBorder();
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);

            if (user instanceof HDBOfficer) {
                showFullProjectDetails(project);
            }
            else if (user instanceof Applicant applicant) {
                filterProjectDetailsForApplicants(project, applicant);
            } else if (user instanceof HDBManager) {
                showFullProjectDetails(project);
                BoxPrinter.printRow("Number of Officer Slots", String.valueOf(project.getOfficerSlot()));
            }

            if (i < projects.size() - 1) {
                BoxPrinter.printDivider();
            } else {
                BoxPrinter.printBottomBorder();
            }
        }
    }

    /**
     * Prints the details of the project based on whether the applicant is single or married in the box format
     *
     * @param project The project whose details are being printed
     * @param applicant The applicant viewing the project details
     */
    public static void filterProjectDetailsForApplicants(Project project, Applicant applicant) {
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

    /**
     * Prints the full project details
     * @param project The project whose details are being printed
     */
    public static void showFullProjectDetails(Project project) {
        BoxPrinter.printRow("Project Name", project.getProjectName());
        BoxPrinter.printRow("Neighborhood", project.getNeighborhood());
        BoxPrinter.printRow("Units for 2-Room Flats", String.valueOf(project.getNumFlatAvailable(FlatType.TWOROOMS)));
        BoxPrinter.printRow("Price for 2-Room Flats", String.format("$%.2f", project.getSellingPrice(FlatType.TWOROOMS)));
        BoxPrinter.printRow("Units for 3-Room Flats", String.valueOf(project.getNumFlatAvailable(FlatType.THREEROOMS)));
        BoxPrinter.printRow("Price for 3-Room Flats", String.format("$%.2f", project.getSellingPrice(FlatType.THREEROOMS)));
        BoxPrinter.printRow("Application Period", project.getOpenDate() + " to " + project.getCloseDate());
    }
}
