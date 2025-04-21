/**
 * Provides methods for applicants to browse and view BTO projects they are eligible for.
 *
 * This controller handles project filtering based on the applicant's role, user group,
 * flat availability, project visibility, and application period.
 *
 * It does not handle application or enquiry functionality.
 *
 */

package control;

import boundary.ProjectViewer;
import entity.*;
import repository.ProjectService;

import java.util.ArrayList;
import java.util.List;


public class ApplicantProjectController {
    public static List<Project> getOpenProjects(Applicant applicant) {
        List<Project> openProjects = new ArrayList<>();
        for (Project project : ProjectService.getAllProjects()) {
            if (!project.isVisible() || !project.checkOpeningPeriod()) continue; // Check if project has visibility toggled and is active
            if (applicant.isEligibleForProject(project)) {
                openProjects.add(project); // Check if the officer is handling or registered for the project already
            }
        }
        return openProjects;
    }

    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects, applicant);
    }
}
