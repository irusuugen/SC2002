/**
 * Interface for handling application-related services for HDB Officers.
 */

package control;

import entity.HDBOfficer;

public interface IOfficerApplicationService {
    /**
     * Allows the HDB Officer to update the status or details of an application.
     *
     * @param officer the HDB Officer performing the update
     */
    void updateApplication(HDBOfficer officer);
}
