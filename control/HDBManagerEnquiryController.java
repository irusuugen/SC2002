package control;

import entity.*;
import java.util.*;

public class HDBManagerEnquiryController {
    private Scanner sc = new Scanner(System.in);

    public void viewAllEnquiries(List<Project> allProjects) {
        for (Project project : allProjects) {
            System.out.println("\nProject: " + project.getProjectName());
            for (Enquiry e : project.getEnquiries()) {
                System.out.println(e);
            }
        }
    }

    public void replyToEnquiry(List<Project> allProjects) {
        System.out.print("Enter project name to view enquiries: ");
        String pname = sc.nextLine();
        for (Project project : allProjects) {
            if (project.getProjectName().equalsIgnoreCase(pname)) {
                List<Enquiry> enquiries = project.getEnquiries();
                for (int i = 0; i < enquiries.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, enquiries.get(i).getMessage());
                }
                int eIdx = IntGetter.readInt("Select enquiry to reply: ") - 1;
                System.out.print("Enter your reply: ");
                String reply = sc.nextLine();
                enquiries.get(eIdx).reply(reply);
                System.out.println("Replied.");
            }
        }
    }
}
