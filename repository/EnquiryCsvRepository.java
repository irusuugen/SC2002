/* Class is for reading EnquiryList csv file */

package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EnquiryCsvRepository implements EnquiryRepository {
    private static final String FILE_PATH = "data/EnquiryList.csv";

    public List<Enquiry> loadAllEnquiries() {
        return readEnquiries(FILE_PATH);
    }

    public void saveAllEnquiries(List<Enquiry> enquiries) {
        writeToEnquiryList(FILE_PATH, enquiries);
    }

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