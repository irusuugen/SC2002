/**
 * Provides high-level access to all registration-related operations.
 *
 * This service is responsible for:
 * <ul>
 *     <li>Loading registrations from the data repository</li>
 *     <li>Adding, removing, and retrieving registrations in storage</li>
 *     <li>Saving updates to persistent storage</li>
 * </ul>
 *
 */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    private static IRegistrationRepository registrationRepository;
    private static List<Registration> registrationList = new ArrayList<>();

    /**
     * Initializes the registration repository and loads all registrations.
     * Each registration is also linked to its corresponding officer and project.
     *
     * @param repo The data repository to use.
     * @return {@code true} if storage initialization succeeds.
     */
    public static boolean startRegistrationStorage(IRegistrationRepository repo) {
        registrationRepository = repo;
        registrationList.clear();
        registrationList = registrationRepository.loadAllRegistrations();
        //assign registration to officers and projects
        for(Registration reg: registrationList){
            reg.getRegisteredOfficer().addRegistration(reg);
            reg.getProject().getRegistrations().add(reg);
        }
        return true;
    }

    /**
     * Retrieves the full list of loaded registrations
     *
     * @return The list of all {@link Registration} instances.
     */
    public static List<Registration> getAllRegistrations() {
        return registrationList;
    }

    /**
     * Saves the current registration list to persistent storage.
     */
    public static void updateRegistrations(){
        registrationRepository.saveAllRegistrations(registrationList);
    }

    /**
     * Adds a new registration to the internal list.
     *
     * @param r The registration to add.
     */
    public static void addRegistration(Registration r) {
        registrationList.add(r);
     }

    /**
     * Removes a registration from the internal list.
     *
     * @param r The registration to remove.
     */
    public static void removeRegistration(Registration r) {
        registrationList.remove(r);
    }
}