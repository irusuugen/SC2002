/**
 * Interface for registration-related services handled by HDB Managers.
 */

package control;

import entity.HDBManager;

public interface IManagerRegistrationService {
    /**
     * Displays all registrations associated with the manager's projects.
     *
     * @param manager the HDB Manager viewing the registrations
     */
    void viewRegistrations(HDBManager manager);

    /**
     * Allows the manager to process and update the status of registrations.
     *
     * @param manager the HDB Manager processing the registrations
     */
    void processRegistrations(HDBManager manager);
}
