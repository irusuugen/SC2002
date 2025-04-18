package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRepository {

    public List<Registration> loadAllRegistrations() {
        return readRegistrations("data/RegistrationList.csv");
    }

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
}
