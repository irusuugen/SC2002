/**
 * Defines the interface for application data persistence.
 *
 * This abstraction allows for different storage implementations (e.g., CSV, database)
 * without changing how applications are used throughout the system.
 *
 * Used by the {@link ApplicationService} to load and save application data.
 *
 */

package repository;

import entity.Application;
import java.util.List;

public interface IApplicationRepository {
     /**
     * Loads all applications from the storage medium.
     *
     * @return A list of all {@link Application} instances.
     */
    List<Application> loadAllApplications();

    /**
     * Saves the full list of applications to the storage medium.
     *
     * @param applications The list of applications to be saved.
     */
    void saveAllApplications(List<Application> applications);
}