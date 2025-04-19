package control;

import boundary.ApplicationViewer;
import boundary.EnquiriesViewer;
import boundary.ProjectViewer;
import entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import repository.*;
import utils.*;

public class ApplicantController {
    private static Scanner sc = new Scanner(System.in);

    public static List<Project> getOpenProjects(Applicant applicant) {
        List<Project> openProjects = new ArrayList<>();
        for (Project project : ProjectService.getAllProjects()) {
            if (!project.isVisible() || !project.checkOpeningPeriod()) continue;
            if (applicant instanceof HDBOfficer officer) {
                if (project.getOfficerSlotList().contains(officer)) continue;
            }
            if (applicant.getUserGroup() == UserGroup.MARRIED &&
                    (project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 || project.getNumFlatAvailable(FlatType.THREEROOMS) > 0)) {
                openProjects.add(project);
            } else if (applicant.getUserGroup() == UserGroup.SINGLE &&
                    project.getNumFlatAvailable(FlatType.TWOROOMS) > 0) {
                openProjects.add(project);
            }
        }
        return openProjects;
    }

    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects, applicant);
    }

    public static void applyForProject(Applicant applicant) {
        if (applicant.getApplication() != null) {
            System.out.println("You have already registered for a project.");
            return;
        }

        viewOpenProjects(applicant);

        Project project;
        while (true) {
            System.out.print("Please enter the name of the project you'd like to apply for: ");
            String inputTitle = sc.nextLine();
            project = findProjectByName(applicant, inputTitle);
            if (project == null) {
                if (!InputHelper.confirm("Project not found. Retry?")) return;
            } else {
                break;
            }
        }

        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, applicant);

        FlatType flatType = FlatType.TWOROOMS;
        if (applicant.getUserGroup() == UserGroup.MARRIED) {
            while (true) {
                int flatChoice = InputHelper.readInt("Enter the flat type you'd like (2 for 2-Room, 3 for 3-Room): ");
                if (flatChoice == 2 || flatChoice == 3) {
                    flatType = (flatChoice == 2) ? FlatType.TWOROOMS : FlatType.THREEROOMS;
                    if (project.getNumFlatAvailable(flatType) > 0) break;
                    else System.out.printf("No %s flats available.\n", flatType == FlatType.TWOROOMS ? "2-Room" : "3-Room");
                } else {
                    System.out.println("Invalid choice. Please enter 2 or 3.");
                }
            }
        } else {
            if (project.getNumFlatAvailable(flatType) <= 0) {
                System.out.println("Sorry, there are no 2-Room flats available at this project.");
                return;
            }
        }

        if (InputHelper.confirm("Are you sure you want to apply for this project?")) {
            Application application = new Application(project, flatType, applicant);
            applicant.setApplication(application);
            project.addApplication(application);
            System.out.println("Application submitted.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static void viewApplication(Applicant applicant) {
        Application application = applicant.getApplication();
        if (application != null) {
            ApplicationViewer.printApplication(application);
        } else {
            System.out.println("No application submitted.");
        }
    }

    public static void requestBooking(Applicant applicant) {
        Application application = applicant.getApplication();
        if (application == null) {
            System.out.println("No existing application.");
            return;
        }

        ApplicationViewer.printApplication(application);
        if (InputHelper.confirm("Are you sure you want to book a flat for this project?")) {
            if (application.getStatus() == Status.SUCCESSFUL) {
                application.setBookingRequested(true);
                System.out.println("Booking request has been sent to the HDB Officers.");
            } else {
                System.out.println("Booking not available right now. Please check your application status.");
            }
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static void requestWithdrawal(Applicant applicant) {
        Application application = applicant.getApplication();
        if (application == null) {
            System.out.println("No existing application.");
            return;
        }

        ApplicationViewer.printApplication(application);
        if (InputHelper.confirm("Are you sure you want to withdraw your application?")) {
            application.setWithdrawalRequested(true);
            System.out.println("Withdrawal request has been sent.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static void submitEnquiry(Applicant applicant) {
        viewOpenProjects(applicant);
        System.out.print("Enter the name of the project you'd like to submit an enquiry for: ");
        String inputTitle = sc.nextLine();
        Project project = findProjectByName(applicant, inputTitle);
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
            Enquiry enquiry = new Enquiry(message, project);
            applicant.addEnquiry(enquiry);
            project.addEnquiry(enquiry);
            System.out.println("Enquiry submitted.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static void editEnquiry(Applicant applicant) {
        List<Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries available.");
            return;
        }

        viewEnquiries(applicant);
        int number = InputHelper.readInt("Select enquiry number: ");
        if (number < 1 || number > enquiries.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Enquiry enquiry = enquiries.get(number - 1);
        if (!enquiry.getAnswer().equals("(No reply yet)")) {
            System.out.println("Enquiry already has a reply. Unable to edit.");
            return;
        }

        System.out.println("Enter your new enquiry:");
        String message = sc.nextLine();
        if (InputHelper.confirm("Confirm edit")) {
            enquiry.edit(message);
            System.out.println("Enquiry updated.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static void deleteEnquiry(Applicant applicant) {
        List<Enquiry> enquiryList = applicant.getEnquiries();
        if (enquiryList.isEmpty()) {
            System.out.println("No enquiries available.");
            return;
        }

        viewEnquiries(applicant);
        int number = InputHelper.readInt("Select enquiry number: ");
        if (number < 1 || number > enquiryList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Enquiry enquiry = enquiryList.get(number - 1);
        if (!enquiry.getAnswer().equals("(No reply yet)")) {
            System.out.println("Enquiry already has a reply. Unable to delete.");
            return;
        }

        if (InputHelper.confirm("Confirm removal")) {
            enquiryList.remove(enquiry);
            enquiry.getProject().removeEnquiry(enquiry);
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

    private static Project findProjectByName(Applicant applicant, String name) {
        return getOpenProjects(applicant).stream()
                .filter(p -> name.equalsIgnoreCase(p.getProjectName()))
                .findFirst()
                .orElse(null);
    }
}
