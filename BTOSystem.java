import java.util.*;

public class BTOSystem {
    private List<User> userList;
    private List<HDBManager> managerList;
    private List<HDBOfficer> officerList;
    private List<Applicant> applicantList;

    public BTOSystem() {
        // Initialize all lists first
        this.userList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.officerList = new ArrayList<>();
        this.applicantList = new ArrayList<>();

        // Load all users
        userList.addAll(UserLoader.readUsers("data/ApplicantList.csv"));
        userList.addAll(UserLoader.readUsers("data/ManagerList.csv"));
        userList.addAll(UserLoader.readUsers("data/OfficerList.csv"));

        // Distribute users by subclass
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

    public List<User> getUsers() {
        return this.userList;
    }
}
