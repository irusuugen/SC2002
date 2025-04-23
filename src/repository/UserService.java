/**
 * Manages the user data logic and maintains separate sublists of users
 * based on their roles (applicants, managers, officers).
 *
 * It is responsible for:
 * <ul>
 *   <li>Initializing and loading users</li>
 *   <li>Providing access to different user role lists</li>
 *   <li>Updating user data in storage</li>
 * </ul>
 *
 */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static IUserRepository userRepository;
    private static List<Applicant> applicantList = new ArrayList<>();
    private static List<HDBManager> managerList = new ArrayList<>();
    private static List<HDBOfficer> officerList = new ArrayList<>();
    private static List<User> userList = new ArrayList<>();

    /**
     * Initializes the user repository and loads all users from storage.
     *
     * @param repo The {@link IUserRepository} implementation to use.
     * @return {@code true} if the storage was loaded successfully.
     */
    public static boolean startUserStorage(IUserRepository repo) {
        userRepository = repo;

        applicantList.clear();
        managerList.clear();
        officerList.clear();
        userList.clear();

        userList = userRepository.loadAllUsers();

        for (User user : userList) {
            if (user instanceof HDBManager m) {
                managerList.add(m);
            } else if (user instanceof HDBOfficer o) {
                officerList.add(o);
            } else if (user instanceof Applicant a) {
                applicantList.add(a);
            }
        }

        return true;
    }

    /** @return The list of all applicant users. */
    public static List<Applicant> getApplicants() {
        return applicantList;
    }

    /** @return The list of all HDB managers. */
    public static List<HDBManager> getManagers() {
        return managerList;
    }

    /** @return The list of all HDB officers. */
    public static List<HDBOfficer> getOfficers() {
        return officerList;
    }

    /** @return The full list of all users (any type). */
    public static List<User> getAllUsers() {
        return userList;
    }

    /**
     * Updates the appropriate user list in storage based on user role.
     *
     * @param user The user whose information should be saved.
     */
    public static void updateUsers(User user){
        Role role = user.getRole();
        switch (role) {
            case APPLICANT:
                updateApplicants();
                break;
            case HDB_OFFICER:
                updateOfficers();
                break;
            case HDB_MANAGER:
                updateManagers();
                break;
            default:
                break;
        }
    }

    /** Writes the applicant list to CSV. */
    public static void updateApplicants(){
        List<User> users = new ArrayList<>(applicantList);
        userRepository.saveApplicant(users);
    }

    /** Writes the officer list to CSV. */
    public static void updateOfficers(){
        List<User> users = new ArrayList<>(officerList);
        userRepository.saveOfficer(users);
    }

    /** Writes the manager list to CSV. */
    public static void updateManagers(){
        List<User> users = new ArrayList<>(managerList);
        userRepository.saveManager(users);
    }
}
