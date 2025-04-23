/**
 * Interface for managing BTO projects by HDB Managers.
 */

package control;

import entity.HDBManager;
import entity.Project;
import entity.UserSession;

import java.util.List;

public interface IManagerProjectService {
    /**
     * Creates a new project under the given manager.
     *
     * @param manager Manager creating the project
     */
    void createProject(HDBManager manager);

    /**
     * Deletes a selected project from the list.
     *
     * @param manager Manager deleting the project
     * @param allProjects List of all created projects by manager
     */
    void deleteProject(HDBManager manager, List<Project> allProjects);

    /**
     * Edits a selected project
     *
     * @param manager Manager editing the project
     */
    void editProject(HDBManager manager);

    /**
     * Lets the manager select a specific project from a list of created projects
     *
     * @param projects List of projects the manager created
     * @return The selected {@link Project}
     */

    Project selectProject(List<Project> projects);

    /**
     * Displays all projects visible to the manager
     *
     * @param manager Manager viewing projects
     * @param allProjects List of all projects
     * @param session Current user session
     */
    void viewAllProjects(HDBManager manager, List<Project> allProjects, UserSession session);
    void toggleProjectVisibility(HDBManager manager);
}
