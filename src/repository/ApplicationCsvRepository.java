/**
 * A CSV-based implementation of {@link IApplicationRepository}.
 *
 * Handles the reading and writing of application data to and from
 * a CSV file, mapping it to {@link Application} instances and linking
 * them to applicants and projects.
 *
 *
 */

package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApplicationCsvRepository implements IApplicationRepository {
    private static final String FILE_PATH = "data/ApplicationList.csv";

    /**
     * Loads all applications from the configured CSV file.
     *
     * @return A list of all loaded {@link Application} objects.
     */

    public List<Application> loadAllApplications() {
        return readApplications(FILE_PATH);
    }

    /**
     * Writes all given applications to the configured CSV file.
     *
     * @param applications The list of applications to be saved.
     */
    public void saveAllApplications(List<Application> applications) {
        writeToApplicationList(FILE_PATH, applications);
    }

    /**
     * Reads applications from the specified CSV file and maps each row
     * to an {@link Application} instance. Links the application to the
     * appropriate applicant and project using their NRIC and project name.
     *
     * @param filename The path of the file to read.
     * @return A list of parsed {@link Application} instances.
     */
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
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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

    /**
     * Writes the given list of applications to the specified CSV file.
     *
     * @param filename     The path of the file to write to.
     * @param applications The list of applications to serialize.
     */
    public static void writeToApplicationList(String filename, List<Application> applications){
        List<String> rows = new ArrayList<>();
        String header = "project,flatType,NRIC,date,status,bookingRequested,withdrawalRequested";
        rows.add(header);
        for(Application a: applications){
            List<String> row = new ArrayList<>();
            row.add(a.getProject().getProjectName());
            if(a.getFlatType() == FlatType.TWOROOMS){
                row.add("2-Room");
            }
            else{
                row.add("3-Room");
            }
            row.add(a.getApplicant().getNric());
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            row.add(a.getDate().format(dateFormatter));
            row.add(a.getStatusString());
            row.add(a.isBookingRequested()? "1" : "0");
            row.add(a.isWithdrawalRequested()? "1" : "0");
            rows.add(String.join(",", row));
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(String.join("\n",rows));
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}