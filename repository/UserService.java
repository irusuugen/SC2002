/* Class to call for file reading and to separate users into sublists according to roles */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private List<Applicant> applicantList;
    private List<HDBManager> managerList;
    private List<HDBOfficer> officerList;
    private List<User> userList;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

        // Initialize the lists
        this.applicantList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.officerList = new ArrayList<>();
        this.userList = new ArrayList<>();

        // Load users from UserRepository
        userList = userRepository.loadAllUsers();

        // Distribute users by their actual subclass
        for (User user : userList) {
            if (user instanceof Applicant) {
                applicantList.add((Applicant) user);
            } else if (user instanceof HDBManager) {
                managerList.add((HDBManager) user);
            } else if (user instanceof HDBOfficer) {
                officerList.add((HDBOfficer) user);
            }
        }
    }

    // Getters for the user lists
    public List<Applicant> getApplicants() {
        return applicantList;
    }

    public List<HDBManager> getManagers() {
        return managerList;
    }

    public List<HDBOfficer> getOfficers() {
        return officerList;
    }

    public List<User> getAllUsers() {
        return userList;
    }
}
