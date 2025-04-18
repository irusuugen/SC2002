/* Class is for reading EnquiryList csv file */

package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EnquiryRepository {
    public List<Enquiry> loadAllEnquiries() {
        List<Enquiry> allEnquiries = new ArrayList<>();
        allEnquiries.addAll(readEnquiries("data/EnquiryList.csv"));
        return allEnquiries;
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
                List<Applicant> applicantList = UserService.getApplicants();
                applicantList.addAll(UserService.getOfficers());
                Applicant applicant  = applicantList.stream().filter(a -> a.getNric().equals(NRIC)).findFirst().get();
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
}