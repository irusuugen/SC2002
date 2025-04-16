package control;

import java.util.*;
import entity.*;

public class HDBManagerEnquiryController {
    public void viewAllEnquiries(List<Project> allProjects) {
        for (Project project : allProjects) {
            System.out.println("\nProject: " + project.getProjectName());
            for (Enquiry e : project.getEnquiries()) {
                System.out.println(e);
            }
        }
    }

    public void replyToEnquiry(Enquiry enquiry, String response) {
        enquiry.reply(response);
    }
}
