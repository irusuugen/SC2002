/* Class to allow for retrieval of database items (user lists, project lists) */

package control;

import entity.*;
import java.util.List;
import repository.*;

public class BTOCoordinator {
    private UserRepository userRepository;
    private UserService userService;

    public BTOCoordinator() {
        this.userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public List<Applicant> getApplicants() {
        return userService.getApplicants();
    }

    public List<HDBOfficer> getOfficers() {
        return userService.getOfficers();
    }

    public List<HDBManager> getManagers() {
        return userService.getManagers();
    }
}