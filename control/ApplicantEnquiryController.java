/**
 * This class manages all enquiry-related actions by applicants.
 *
 * This includes submitting enquiries for projects, as well as editing,
 * deleting, and viewing previously submitted enquiries.
 *
 */

package control;

import boundary.EnquiriesViewer;
import boundary.ProjectViewer;
import entity.Applicant;
import entity.Enquiry;
import entity.Project;
import entity.Role;
import repository.EnquiryService;
import utils.ClearPage;
import utils.InputHelper;
import java.util.List;
import java.util.Scanner;

public class ApplicantEnquiryController implements IApplicantEnquiryService {
    public static Scanner sc = new Scanner(System.in);

    /**
     * Allows the applicant to submit an enquiry about a project.
     *
     * @param applicant The applicant submitting the enquiry.
     */
    public void submitEnquiry(Applicant applicant, IApplicantProjectService projectService) {
        projectService.viewOpenProjects(applicant);
        System.out.print("Enter the name of the project you'd like to submit an enquiry for: ");
        String inputTitle = sc.nextLine();
        Project project = projectService.findProjectByName(applicant, inputTitle);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, Role.APPLICANT, applicant);

        System.out.println("Enter your enquiry:");
        String message = sc.nextLine();
        System.out.println("Your enquiry is: " + message);

        if (InputHelper.confirm("Confirm submission")) {
            Enquiry enquiry = new Enquiry(applicant, message, project);
            EnquiryService.addEnquiry(enquiry);
            EnquiryService.updateEnquiries();
            applicant.addEnquiry(enquiry);
            project.addEnquiry(enquiry);
            System.out.println("Enquiry submitted.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    /**
     * Allows the applicant to edit an enquiry that has not been answered.
     *
     * @param applicant The applicant editing their enquiry.
     */
    public void editEnquiry(Applicant applicant) {
        Enquiry enquiry = selectEnquiry(applicant);

        if (enquiry == null) {
            return;
        }

        if (!enquiry.getAnswer().equals("(No reply yet)")) {
            System.out.println("Enquiry already has a reply. Unable to edit.");
            return;
        }

        System.out.println("Enter your new enquiry:");
        String message = sc.nextLine();
        if (InputHelper.confirm("Confirm edit")) {
            enquiry.edit(message);
            EnquiryService.updateEnquiries();
            System.out.println("Enquiry updated.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    /**
     * Allows the applicant to delete an enquiry that has not been answered.
     *
     * @param applicant The applicant deleting their enquiry.
     */
    public void deleteEnquiry(Applicant applicant) {
        List<Enquiry> enquiryList = applicant.getEnquiries();
        Enquiry enquiry = selectEnquiry(applicant);

        if (enquiry == null) {
            return;
        }
        if (!enquiry.getAnswer().equals("(No reply yet)")) {
            System.out.println("Enquiry already has a reply. Unable to delete.");
            return;
        }

        if (InputHelper.confirm("Confirm removal")) {
            enquiryList.remove(enquiry);
            enquiry.getProject().removeEnquiry(enquiry);
            EnquiryService.removeEnquiry(enquiry);
            EnquiryService.updateEnquiries();
            System.out.println("Enquiry deleted.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    /**
     * Displays all enquiries submitted by the applicant.
     *
     * @param applicant The applicant whose enquiries will be shown.
     */
    public void viewEnquiries(Applicant applicant) {
        List<Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries submitted.");
        } else {
            System.out.println("Here are your enquiries:");
            EnquiriesViewer.printEnquiries(enquiries);
        }
    }

    /**
     * Helper method to allow the applicant to select an enquiry by index.
     *
     * @param applicant The applicant selecting an enquiry.
     * @return The selected {@link Enquiry}, or {@code null} if the selection is invalid or
     * no enquiries are available.
     *
     */
    public Enquiry selectEnquiry(Applicant applicant) {
        List<Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries available.");
            return null;
        }

        viewEnquiries(applicant);
        int number = InputHelper.readInt("Select enquiry number: ");
        if (number < 1 || number > enquiries.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return enquiries.get(number - 1);
    }
}
