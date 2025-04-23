/**
 * Interface for project-related services available to Applicants.
 */

package control;

import entity.Applicant;
import entity.Project;
import java.util.List;

public interface IApplicantProjectService {
    /**
     * Retrieves a list of all currently open projects that the applicant can apply for.
     *
     * @param applicant Applicant requesting the list
     * @return List of projects applicant can apply for
     */
    List<Project> getOpenProjects(Applicant applicant);

    /**
     * Prints the list of open projects
     * @param applicant Applicant viewing the list of projects
     */
    void viewOpenProjects(Applicant applicant);

    /**
     * Allows selection of a project via its name
     * @param applicant Applicant retrieving the project
     * @param projectName Name of project to retrieve
     * @return
     */
    Project findProjectByName(Applicant applicant, String projectName);
}
