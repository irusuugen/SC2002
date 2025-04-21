/**
 * Provides high-level access to all project-related operations.
 *
 * This service is responsible for:
 * <ul>
 *     <li>Loading projects from the data repository</li>
 *     <li>Adding, removing, and retrieving projects in storage</li>
 *     <li>Saving updates to persistent storage</li>
 * </ul>
 *
 */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private static IProjectRepository projectRepository;
    private static List<Project> projectList = new ArrayList<>();

    /**
     * Initializes the project repository and loads all projects.
     * Each project is also linked to its corresponding applicant and project.
     *
     * @param repo The data repository to use.
     * @return {@code true} if storage initialization succeeds.
     */
    public static boolean startProjectStorage(IProjectRepository repo) {
        projectRepository = repo;
        projectList.clear();
        projectList = projectRepository.loadAllProjects();
        return true;
    }

    /**
     * Retrieves the full list of loaded projects
     *
     * @return The list of all {@link Project} instances.
     */
    public static List<Project> getAllProjects() {
        return projectList;
    }

    /**
     * Saves the current project list to persistent storage.
     */
    public static void updateProjects(){
        List<Project> projects = new ArrayList<>(projectList);
        projectRepository.saveAllProjects(projects);
    }

    /**
     * Adds a new project to the internal list.
     *
     * @param p The project to add.
     */
    public static void addProject(Project p) {
       projectList.add(p);
    }

    /**
     * Removes a project from the internal list.
     *
     * @param p The project to remove.
     */
    public static void removeProject(Project p) {
        projectList.remove(p);
    }
}