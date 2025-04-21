/**
 * This class is a CSV-based implementation of {@link IUserRepository}.
 *
 * This class handles reading from and writing to user CSV files
 * for applicants, HDB officers, and HDB managers.
 *
 * It parses user data from specific CSV formats and maps them to User subclasses.
 * Used as a low-level data provider in the system's user management module.
 *
 */

package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UserCsvRepository implements IUserRepository {
    private static final String APPLICANT_FILE = "data/ApplicantList.csv";
    private static final String MANAGER_FILE = "data/ManagerList.csv";
    private static final String OFFICER_FILE = "data/OfficerList.csv";

    /**
     * Loads all users from their respective CSV files.
     * <p>
     * Internally delegates to {@code readUsers()} with the appropriate
     * {@link Role} to ensure correct mapping of user types.
     *
     * @return A combined list of all users in the system.
     */
    public List<User> loadAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(readUsers(APPLICANT_FILE, Role.APPLICANT));
        allUsers.addAll(readUsers(MANAGER_FILE, Role.HDB_MANAGER));
        allUsers.addAll(readUsers(OFFICER_FILE, Role.HDB_OFFICER));
        return allUsers;
    }

    public void saveApplicant(List<User> users) {
        writeToUserList(APPLICANT_FILE, users);
    }

    public void saveOfficer(List<User> users) {
        writeToUserList(OFFICER_FILE, users);
    }

    public void saveManager(List<User> users) {
        writeToUserList(MANAGER_FILE, users);
    }

    /**
     * Reads users from a specified CSV file and maps them to a User subclass instance based
     * on the provided role.
     *
     * @param filename Path to the CSV file.
     * @param role The role of the users in the CSV file.
     * @return A list of users parsed from the file.
     */
    public List<User> readUsers(String filename, Role role) {
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
                int age = Integer.parseInt(userInfo[2]);
                boolean isMarried = false;
                if (userInfo[3].equals("Single")) {
                    isMarried = false;
                }
                else if (userInfo[3].equals("Married")) {
                    isMarried = true;
                }
                String password = userInfo[4];

                // Checking for role of the user
                switch (role) {
                    case APPLICANT -> users.add(new Applicant(name, nric, password, age, isMarried));
                    case HDB_MANAGER -> users.add(new HDBManager(name, nric, password, age, isMarried));
                    case HDB_OFFICER -> users.add(new HDBOfficer(name, nric, password, age, isMarried));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return users; 
    }

    /**
     * Writes a list of users to a specified CSV file.
     *
     * @param filename The CSV file path to write to.
     * @param users    The list of users to be saved.
     */
    public static void writeToUserList(String filename, List<User> users){
        List<String> rows = new ArrayList<>();
        String header = "Name,NRIC,Age,Marital Status,Password";
        rows.add(header);
        for(User u: users){
            List<String> row = new ArrayList<>();
            row.add(u.getName());
            row.add(u.getNric());
            row.add(String.valueOf(u.getAge()));
            if(u.isMarried()){
                row.add("Married");
            }
            else{
                row.add("Single");
            }
            row.add(u.getPassword());
            rows.add(String.join(",", row));
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String row : rows) {
                writer.println(row);
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}