/**
 * Defines the interface for registration data persistence.
 *
 * This abstraction allows for different storage implementations (e.g., CSV, database)
 * without changing how registrations are used throughout the system.
 *
 * Used by the {@link RegistrationService} to load and save project data.
 *
 */

package repository;

import entity.*;
import java.util.*;

public interface IRegistrationRepository {
    /**
     * Loads all registrations from the storage medium.
     *
     * @return A list of all {@link Registration} instances.
     */
    List<Registration> loadAllRegistrations();

    /**
     * Saves the full list of registrations to the storage medium.
     *
     * @param registrations The list of registrations to be saved.
     */
    void saveAllRegistrations(List<Registration> registrations);
}
