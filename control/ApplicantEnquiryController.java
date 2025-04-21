package control;

import boundary.EnquiriesViewer;
import boundary.ProjectViewer;
import entity.Applicant;
import entity.Enquiry;
import entity.Project;
import repository.EnquiryService;
import utils.ClearPage;
import utils.InputHelper;
import java.util.List;
import java.util.Scanner;

public class ApplicantEnquiryController {
    public static Scanner sc = new Scanner(System.in);

    public static void submitEnquiry(Applicant applicant) {
        ApplicantProjectController.viewOpenProjects(applicant);
        System.out.print("Enter the name of the project you'd like to submit an enquiry for: ");
        String inputTitle = sc.nextLine();
        Project project = ApplicantApplicationController.findProjectByName(applicant, inputTitle);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, applicant);

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

    public static void editEnquiry(Applicant applicant) {
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

    public static void deleteEnquiry(Applicant applicant) {
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


    public static void viewEnquiries(Applicant applicant) {
        List<Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries submitted.");
        } else {
            System.out.println("Here are your enquiries:");
            EnquiriesViewer.printEnquiries(enquiries);
        }
    }

    public static Enquiry selectEnquiry(Applicant applicant) {
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

        Enquiry enquiry = enquiries.get(number - 1);
        return enquiry;
    }
}
