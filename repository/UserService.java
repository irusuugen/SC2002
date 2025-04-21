/* Class to call for file reading and to separate users into sublists according to roles */

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

    public static List<Applicant> getApplicants() {
        return applicantList;
    }

    public static List<HDBManager> getManagers() {
        return managerList;
    }

    public static List<HDBOfficer> getOfficers() {
        return officerList;
    }

    public static List<User> getAllUsers() {
        return userList;
    }

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

    public static void updateApplicants(){
        List<User> users = new ArrayList<>(applicantList);
        userRepository.saveApplicant(users);
    }

    public static void updateOfficers(){
        List<User> users = new ArrayList<>(officerList);
        userRepository.saveOfficer(users);
    }

    public static void updateManagers(){
        List<User> users = new ArrayList<>(managerList);
        userRepository.saveManager(users);
    }
}
