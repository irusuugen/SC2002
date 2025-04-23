/**
 * This class manages all enquiry-related actions by managers.
 *
 * This includes viewing all enquiries made, replying to unreplied enquiries.
 *
 */

package control;

import boundary.EnquiriesViewer;
import entity.*;
import java.util.*;
import repository.*;
import utils.*;

public class HDBManagerEnquiryController implements IManagerEnquiryService {
    /**
     * Prints out all enquiries made for all projects
     *
     * @param allProjects The list of all projects
     *
     */
    public void viewAllEnquiries(List<Project> allProjects) {
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

    /**
     * Retrieves the list of enquiries of a project that are not replied to
     *
     * @param project The project the manager is retrieving unreplied enquiries for
     * @return List of unreplied enquiries for a selected project
     *
     */
    public List<Enquiry> getUnrepliedEnquiries(Project project) {
        List<Enquiry> unrepliedEnquiries = new ArrayList<>();
        for (Enquiry enquiry : project.getEnquiries()) {
            if (enquiry.getAnswer().equals("(No reply yet)")) {
                unrepliedEnquiries.add(enquiry);
            }
        }
        return unrepliedEnquiries;
    }

    /**
     * Prints out all unreplied enquiries of the project that was selected by the manager
     *
     * @param project The project the manager chose to view the unreplied enquiries for
     *
     */
    public void viewUnrepliedEnquiries(Project project) {
        List<Enquiry> unrepliedEnquiries = getUnrepliedEnquiries(project);
        if (unrepliedEnquiries.isEmpty()) {
            System.out.println("No unreplied enquiries found.");
            return;
        }
        System.out.println("Here are your unreplied enquiries:");
        EnquiriesViewer.printEnquiries(unrepliedEnquiries);
    }

    /**
     * Allows manager to select an unreplied enquiry and give a reply to it
     *
     * @param enquiry The enquiry selected by the manager to reply to
     *
     */
    public void replyEnquiry(Enquiry enquiry) {
        if (enquiry==null){
            return;
        }
        System.out.println("Enter your response:");
        String response = new Scanner(System.in).nextLine();
        if (InputHelper.confirm("Confirm reply")) {
            enquiry.reply(response);
            EnquiryService.updateEnquiries();
            System.out.println("Reply successful.");
        }
    }

    /**
     * Prompts the manager to select a project to view unreplied enquiries for, and then
     * allows for selection of unreplied enquiry.
     *
     * @param manager The manager selecting the enquiry
     * @param projectService The project service used to retrieve the manager's projects
     * @return Unreplied enquiry selected by the manager
     */
    public Enquiry selectEnquiry(HDBManager manager, IManagerProjectService projectService) {
        Project project = projectService.selectProject(manager.getCreatedProjects());
        if (project == null) {
            System.out.println("No project found.");
            return null;
        }

        List<Enquiry> unreplied = getUnrepliedEnquiries(project);
        viewUnrepliedEnquiries(project);
        if (unreplied.isEmpty()) {
            System.out.println("No enquiries found.");
            return null;
        }

        int choice;
        while (true) {
            choice = InputHelper.readInt("Enter enquiry number (1-" + unreplied.size() + "): ");
            if (choice >= 1 && choice <= unreplied.size()) break;
            System.out.println("Invalid input. Please choose a number from 1 to " + unreplied.size() + ".");
        }
        Enquiry enquiry = unreplied.get(choice - 1);
        return enquiry;
    }
}
