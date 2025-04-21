/**
 * Handles reading and writing of information regarding registrations
 * from and to a CSV file.
 * This repository is responsible for parsing registration information from storage
 * and converting it into Registration objects. It also serializes a list of
 * registrations back into the appropriate CSV format.
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

public class RegistrationCsvRepository implements IRegistrationRepository {
    private static final String FILE_PATH = "data/RegistrationList.csv";

    /**
     * Loads all registrations available in the system from the registration list file.
     *
     * @return A list of all Registration objects loaded from "data/RegistrationList.csv".
     */
    public List<Registration> loadAllRegistrations() {
        return readRegistrations(FILE_PATH);
    }

    /**
     * Writes all given registrations to the configured CSV file.
     *
     * @param registrations The list of registrations to be saved.
     */
    public void saveAllRegistrations(List<Registration> registrations) {
        writeToRegistrationList(FILE_PATH, registrations);
    }

    /**
     * Reads registration data from the specified CSV file and constructs a list of {@link Project} objects.
     *
     * This method parses registration details (project name, HDB officer's nric, etc.),
     * and associates them with the correct officer based on NRIC.
     *
     * @param filename The path to the CSV file containing registration data.
     * @return A list of Registration instances parsed from the file.
     */
    public List<Registration> readRegistrations(String filename) {
        List<Registration> registrations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String currentLine;
            br.readLine(); // Skip header

            while ((currentLine = br.readLine()) != null) {
                String[] data = currentLine.split(",");

                String nric = data[0];
                String projectName = data[1];
                String statusString = data[2];

                List<HDBOfficer> officers = UserService.getOfficers();
                HDBOfficer officer = officers.stream()
                    .filter(o -> o.getNric().equals(nric))
                    .findFirst().get();
                Project project = ProjectService.getAllProjects().stream()
                    .filter(p -> p.getProjectName().equals(projectName))
                    .findFirst()
                    .get();

                Status status = Status.valueOf(statusString);

                Registration registration = new Registration(officer, project, status);
                registrations.add(registration);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return registrations;
    }

    /**
     * Writes the provided list of registrations to a CSV file.
     *
     * Each registration's key attributes, including project name and officer,
     * are formatted and written in CSV structure suitable for reloading.
     *
     * @param filename The destination file path to write the registration list.
     * @param registrations The list of Registration instances to be serialized.
     */
    public static void writeToRegistrationList(String filename, List<Registration> registrations){
        List<String> rows = new ArrayList<>();
        String header = "NRIC,project,status";
        rows.add(header);
        for(Registration r: registrations){
            List<String> row = new ArrayList<>();
            row.add(r.getRegisteredOfficer().getNric());
            row.add(r.getProject().getProjectName());
            row.add(r.getStatus().toString());
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
