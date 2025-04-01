import java.util.*;
public class BTOSystem {
    private ArrayList<User> users;

    public BTOSystem() {
        UserLoader ul = new UserLoader();
        this.users = new ArrayList<>();
        users.addAll(ul.readUsers("data/ApplicantList.csv"));
        users.addAll(ul.readUsers("data/ManagerList.csv"));
        users.addAll(ul.readUsers("data/OfficerList.csv"));
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }
}