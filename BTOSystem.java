import java.util.*;
public class BTOSystem {
    private List<User> userList;
    private List<HDBManager> managerList;
    private List<HDBOfficer> officerList;
    private List<Applicant> applicantList;

    public BTOSystem() {
        // Read files
        UserLoader ul = new UserLoader();
        this.userList = new ArrayList<>();
        userList.addAll(ul.readUsers("data/ApplicantList.csv"));
        userList.addAll(ul.readUsers("data/ManagerList.csv"));
        userList.addAll(ul.readUsers("data/OfficerList.csv"));
        
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

    public List<User> getUsers() {
        return this.userList;
    }
}