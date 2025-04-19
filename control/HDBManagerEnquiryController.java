package control;

import boundary.EnquiriesViewer;
import entity.*;
import java.util.*;
import repository.ProjectService;
import utils.*;

public class HDBManagerEnquiryController {

    public static void viewAllEnquiries(List<Project> allProjects) {
        List<Enquiry> allEnquiries = new ArrayList<>();
        for (Project project : allProjects) {
            allEnquiries.addAll(project.getEnquiries());
        }

        if (allEnquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }
        System.out.println("Here are your enquiries: ");
        EnquiriesViewer.printEnquiries(allEnquiries);
    }

    public static List<Enquiry> getUnrepliedEnquiries(Project project) {
        List<Enquiry> unrepliedEnquiries = new ArrayList<>();
        for (Enquiry enquiry : project.getEnquiries()) {
            if (enquiry.getAnswer().equals("(No reply yet)")) {
                unrepliedEnquiries.add(enquiry);
            }
        }
        return unrepliedEnquiries;
    }

    public static void viewUnrepliedEnquiries(Project project) {
        List<Enquiry> unrepliedEnquiries = getUnrepliedEnquiries(project);
        if (unrepliedEnquiries.isEmpty()) {
            System.out.println("No unreplied enquiries found.");
            return;
        }
        System.out.println("Here are your unreplied enquiries:");
        EnquiriesViewer.printEnquiries(unrepliedEnquiries);
    }

    public static void replyEnquiry(HDBManager manager) {
        Project project = selectProject(manager.getCreatedProjects());
        if (project == null) return;

        List<Enquiry> unreplied = getUnrepliedEnquiries(project);
        viewUnrepliedEnquiries(project);
        if (unreplied.isEmpty()) return;

        int choice = InputHelper.readInt(
                "Enter enquiry number (1-" + unreplied.size() + "): ");
        Enquiry enquiry = unreplied.get(choice - 1);

        System.out.println("Enter your response:");
        String response = new Scanner(System.in).nextLine();

        if (InputHelper.confirm("Confirm reply")) {
            enquiry.reply(response);
            System.out.println("Reply successful.");
        }
    }

    // Allows manager to select a *created* project to look at enquiries for
    private static Project selectProject(List<Project> projects) {
        if (projects.isEmpty()) {
            System.out.println("You're not handling any projects currently.");
            return null;
        }

        System.out.println("Select a project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.printf("(%d) %s\n", i + 1, projects.get(i).getProjectName());
        }

        while (true) {
            int choice = InputHelper.readInt("Enter choice (0 to cancel): ");
            if (choice == 0) return null;
            if (choice >= 1 && choice <= projects.size()) {
                return projects.get(choice - 1);
            }
            System.out.println("Invalid choice. Please select a number from 1 to " + projects.size() + ", or 0 to cancel.");
        }
    }
}
