/* Class is for reading ApplicationList csv file */

package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository {
    public List<Application> loadAllApplications() {
        List<Application> allApplications = new ArrayList<>();
        allApplications.addAll(readApplications("data/ApplicationList.csv"));
        return allApplications;
    }

    public List<Application> readApplications(String filename) {
        ArrayList<Application> applications = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            br.readLine(); // Ignore headings
            while ((currentLine = br.readLine()) != null) {
                // Obtaining the basic information
                String[] applicationInfo = currentLine.split(",");
                String projectName = applicationInfo[0];
                Project project = ProjectService.getAllProjects().stream().filter(p -> p.getProjectName().equals(projectName)).findFirst().get();
                String flatString = applicationInfo[1];
                FlatType flatType;
                if(flatString.equals("2-Room")){
                    flatType = FlatType.TWOROOMS;
                }
                else{
                    flatType = FlatType.THREEROOMS;
                }
                String NRIC = applicationInfo[2];
                List<Applicant> combinedList = new ArrayList<>(UserService.getApplicants());
                combinedList.addAll(UserService.getOfficers());
                Applicant applicant  = combinedList.stream().filter(a -> a.getNric().equals(NRIC)).findFirst().get();
                Status status = Status.valueOf(applicationInfo[3]);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yy");
                LocalDate applicationDate = LocalDate.parse(applicationInfo[4], dateFormatter);
                boolean bookingRequested = Boolean.parseBoolean(applicationInfo[5]);
                boolean withdrawalRequested = Boolean.parseBoolean(applicationInfo[6]);
                // Creates a project and adds to projectList
                Application application = new Application(project, flatType, applicant, status, applicationDate, bookingRequested, withdrawalRequested);
                applications.add(application);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return applications;
    }
}