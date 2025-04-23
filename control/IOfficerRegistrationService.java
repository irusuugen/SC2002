/**
 * Interface for registration-related services provided to HDB Officers.
 */

package control;

import entity.HDBOfficer;

public interface IOfficerRegistrationService {
    /**
     * Registers the officer's assigned project for registration handling.
     *
     * @param officer the HDB Officer registering the project
     */
    void registerForProject(HDBOfficer officer);

    /**
     * Displays all registrations for the officer's assigned project.
     *
     * @param officer the HDB Officer viewing registrations
     */
    void viewRegistrations(HDBOfficer officer);

    /**
     * Prints the project assigned to the officer.
     *
     * @param officer the HDB Officer whose project is printed
     */
    void printAssignedProject(HDBOfficer officer);
}
