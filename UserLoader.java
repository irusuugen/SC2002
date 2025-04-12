import java.io.*;
import java.util.*;

public class UserLoader {

    /* Function to parse through the file and return a list of Users */
    public ArrayList<User> readUsers(String filename) {
        ArrayList<User> users = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            br.readLine(); // Ignore headings
            while ((currentLine = br.readLine()) != null) {
                // Obtaining basic information
                String[] userInfo = currentLine.split(",");
                String name = userInfo[0];
                String nric = userInfo[1];
                int age = Integer.valueOf(userInfo[2]);
                boolean isMarried = false;
                if (userInfo[3].equals("Single")) {
                    isMarried = false;
                }
                else if (userInfo[3].equals("Married")) {
                    isMarried = true;
                }
                String password = userInfo[4];

                // Checking for role of the user
                if (filename.contains("Applicant")) {
                    users.add(new Applicant(name, nric, password, age, isMarried));
                }
                else if (filename.contains("Manager")) {
                    users.add(new HDBManager(name, nric, password, age, isMarried));
                }
                else if (filename.contains("Officer")) {
                    users.add(new HDBOfficer(name, nric, password, age, isMarried));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return users;
    }
}