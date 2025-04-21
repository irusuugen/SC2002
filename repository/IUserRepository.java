/**
 * Defines the contract for user data access and persistence.
 *
 * It abstracts the implementation details of how user data is loaded and stored.
 *
 */

package repository;

import entity.*;
import java.util.List;

public interface IUserRepository {
    /**
     * Loads all users (Applicants, HDB Managers, and HDB Officers) into a single list.
     *
     * @return A list of all users loaded from persistent storage.
     */
    List<User> loadAllUsers();

    /**
     * Persists a list of applicants to storage.
     *
     * @param users The list of applicant-type users to save.
     */
    void saveApplicant(List<User> users);

    /**
     * Persists a list of officers to storage.
     *
     * @param users The list of officer-type users to save.
     */
    void saveOfficer(List<User> users);

    /**
     * Persists a list of managers to storage.
     *
     * @param users The list of manager-type users to save.
     */
    void saveManager(List<User> users);
}