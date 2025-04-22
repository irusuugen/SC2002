/**
 * Provides methods for applicants to browse and view BTO projects they are eligible for.
 *
 * This controller handles project filtering based on the applicant's role, user group,
 * flat availability, project visibility, and application period.
 *
 */

package control;

import boundary.ProjectViewer;
import entity.*;
import repository.ProjectService;

import java.util.ArrayList;
import java.util.List;


public class ApplicantProjectController {

    /**
     * Iterates through the list of all projects and
     * filters the projects that the applicant is eligible for based on their role, their marriage status,
     * project visibility, project application period, etc.
     *
     * @param applicant The applicant whose eligible projects should be returned
     * @return List of {@link Project} objects that are available and eligible for the applicant,
     * or an empty list if no matching projects are found
     *
     */
    public static List<Project> getOpenProjects(Applicant applicant) {
        List<Project> openProjects = new ArrayList<>();
        for (Project project : ProjectService.getAllProjects()) {
            if (!project.isVisible() || !project.checkOpeningPeriod()) continue; // Check if project has visibility toggled and is active
            if (applicant.isEligibleForProject(project)) {
                openProjects.add(project); // Checks what projects should be viewable by the applicant
            }
        }
        return openProjects;
    }

    /**
     * Prints the formatted list of projects that the applicant is eligible for
     *
     * @param applicant The applicant who is viewing their open projects
     */
    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects, applicant);
    }
}
