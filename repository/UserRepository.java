/* Class to handle reading from CSV and returning the overall user list */
package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import entity.*;

public class UserRepository {
    public List<User> loadAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(readUsers("data/ApplicantList.csv"));
        allUsers.addAll(readUsers("data/ManagerList.csv"));
        allUsers.addAll(readUsers("data/OfficerList.csv"));
        return allUsers;
    }

    public List<User> readUsers(String filename) {
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
    public static void wrtieToUserList(String filename, List<User> users){
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