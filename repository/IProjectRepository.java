/**
 * Defines the interface for project data persistence.
 *
 * This abstraction allows for different storage implementations (e.g., CSV, database)
 * without changing how projects are used throughout the system.
 *
 * Used by the {@link ProjectService} to load and save project data.
 */

package repository;

import entity.Enquiry;
import entity.Project;
import java.util.List;

public interface IProjectRepository {
    /**
     * Loads all projects from the storage medium.
     *
     * @return A list of all {@link Project} instances.
     */
    List<Project> loadAllProjects();

    /**
     * Saves the full list of projects to the storage medium.
     *
     * @param projects The list of projects to be saved.
     */
    void saveAllProjects(List<Project> projects);
}