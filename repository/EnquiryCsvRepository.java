/**
 * A CSV-based implementation of {@link IEnquiryRepository}.
 *
 * Handles the reading and writing of enquiry data to and from
 * a CSV file, mapping it to {@link Enquiry} instances and linking
 * them to applicants and projects.
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

public class EnquiryCsvRepository implements IEnquiryRepository {
    private static final String FILE_PATH = "data/EnquiryList.csv";

    /**
     * Loads all enquiries from the configured CSV file.
     *
     * @return A list of all loaded {@link Enquiry} objects.
     */
    public List<Enquiry> loadAllEnquiries() {
        return readEnquiries(FILE_PATH);
    }

    /**
     * Writes all given enquiries to the configured CSV file.
     *
     * @param enquiries The list of enquiries to be saved.
     */
    public void saveAllEnquiries(List<Enquiry> enquiries) {
        writeToEnquiryList(FILE_PATH, enquiries);
    }

    /**
     * Reads enquiries from the specified CSV file and maps each row
     * to an {@link Enquiry} instance. Links the enquiry to the
     * appropriate applicant and project using their NRIC and project name.
     *
     * @param filename The path of the file to read.
     * @return A list of parsed {@link Enquiry} instances.
     */
    public List<Enquiry> readEnquiries(String filename) {
        ArrayList<Enquiry> enquiries = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            br.readLine(); // Ignore headings
            while ((currentLine = br.readLine()) != null) {
                // Obtaining the basic information
                String[] enquiryInfo = currentLine.split(",");
                String NRIC = enquiryInfo[0];
                List<Applicant> combinedList = new ArrayList<>(UserService.getApplicants());
                combinedList.addAll(UserService.getOfficers());
                Applicant applicant  = combinedList.stream().filter(a -> a.getNric().equals(NRIC)).findFirst().get();
                String message = enquiryInfo[1].replace("\"", "");
                String projectName = enquiryInfo[2];
                Project project = ProjectService.getAllProjects().stream().filter(p -> p.getProjectName().equals(projectName)).findFirst().get();
                String reply = enquiryInfo[3].replace("\"", "");
                Enquiry enquiry = new Enquiry(applicant, message, project, reply);
                enquiries.add(enquiry);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return enquiries;
    }

    /**
     * Writes the given list of enquiries to the specified CSV file.
     *
     * @param filename     The path of the file to write to.
     * @param enquiries The list of enquiries to serialize.
     */
    public static void writeToEnquiryList(String filename, List<Enquiry> enquiries){
        List<String> rows = new ArrayList<>();
        String header = "NRIC,message,project,answer";
        rows.add(header);
        for(Enquiry e: enquiries){
            List<String> row = new ArrayList<>();
            row.add(e.getApplicant().getNric());
            row.add(e.getMessage());
            row.add(e.getProject().getProjectName());
            row.add(e.getAnswer().equals("(No reply yet)")? "":e.getAnswer());
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