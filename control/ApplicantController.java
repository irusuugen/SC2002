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

    // Return a list of projects based on applicant's user group, flat availability, project visibility
    public static List<Project> getOpenProjects(Applicant applicant) {
        List<Project> openProjects = new ArrayList<>();
        if (applicant.getUserGroup() == UserGroup.MARRIED) {
            for (Project project : ProjectService.getAllProjects()) {
                if (project.isVisible() && (project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 || project.getNumFlatAvailable(FlatType.THREEROOMS) > 0)) {
                    openProjects.add(project);
                }
            }
        } else if (applicant.getUserGroup() == UserGroup.SINGLE) {
            for (Project project : ProjectService.getAllProjects()) {
                if (project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 && project.isVisible()) {
                    openProjects.add(project);
                }
            }
        }
        return openProjects;
    }

    // Prints all projects available to the user for application
    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects, applicant);
    }

    // Creates an application
    public static boolean applyForProject(Applicant applicant) {
        // Check for existing application and if existing applcation was unsuccessful
        if (applicant.getApplication() != null) {
            System.out.println("You have already registered for a project.");
            return false;
        }

        // List available projects for application
        viewOpenProjects(applicant);
        
        // Checks for the project that user wants to apply for
        Project project;
        while (true) {
        System.out.println("Please enter the name of the project you'd like to apply for: ");
        String inputTitle = sc.nextLine();
            project = getOpenProjects(applicant).stream()
                        .filter(p -> inputTitle.equals(p.getProjectName()))
                        .findFirst()
                        .orElse(null);
            if (project == null) {
                System.out.print("Project was not found. Retry? (Y/N): ");
                String retry = sc.nextLine();
                if (retry.equalsIgnoreCase("Y"));
                else return false;
            } else {
                break;
            }
        }
        
        // Confirmation of application
        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, applicant);
        int flatChoice;
        FlatType flatType = FlatType.TWOROOMS; // Set as default, for single applicants
        if (applicant.getUserGroup() == UserGroup.MARRIED) {
            while (true) {
                flatChoice = IntGetter.readInt("Enter the flat type you'd like (2 for 2-Room, 3 for 3-Room): ");
                if (flatChoice == 2 || flatChoice == 3) break;
                System.out.println("Please enter a number between 1 and 11.");
            }
            if (flatChoice==2) {
                flatType = FlatType.TWOROOMS;
            } else if (flatChoice == 3) {
                flatType = FlatType.THREEROOMS;
            } 
        }
        System.out.print("Are you sure you want to apply for this project? (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            applicant.setApplication(new Application(project, flatType, applicant));
            project.addApplication(new Application(project, flatType, applicant));
        }
        else {
            System.out.println("Request cancelled.");
            return false;
        }
        return true;
    }

    public static void viewApplication(Applicant applicant) {
        Application application = applicant.getApplication();
        if (application != null) {
                ApplicationViewer.printApplication(application);;
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
        System.out.print("Are you sure you want to book a flat for this project? (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            BookingService.createBooking(application);
        }
        else {
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
        System.out.print("Are you sure you want to withdraw your application? (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            WithdrawalService.requestWithdrawal(application);
        }
        else {
            System.out.println("Request cancelled.");
        }

    }

    public static void submitEnquiry(Applicant applicant) {
        viewOpenProjects(applicant);
        System.out.print("Enter the name of the project you'd like to submit an enquiry for: ");
        String inputTitle = sc.nextLine();
        Project project = getOpenProjects(applicant).stream()
            .filter(p -> inputTitle.equals(p.getProjectName()))
            .findFirst()
            .orElse(null);
        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, applicant);
        System.out.println("Enter your enquiry:");
        String message = sc.nextLine();
        System.out.println("Your enquiry is: " + message);
        System.out.print("Confirm submission (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            applicant.addEnquiry(new Enquiry(message, project));
            project.addEnquiry(new Enquiry(message, project));
        }
        else {
            System.out.println("Request cancelled.");
        }
    }

    public static void editEnquiry(Applicant applicant) {
        if (applicant.getEnquiries().isEmpty()) {
            System.out.println("No enquiries available.");
            return;
        } 
        viewEnquiries(applicant);
        System.out.println("Select enquiry number: ");
        int number = sc.nextInt();
        sc.nextLine();
        Enquiry enquiry = applicant.getEnquiries().get(number-1);
        if (!enquiry.getAnswer().equals("No reply yet")) {
            System.out.println("Enquiry already has a reply. Unable to edit.");
            return;
        }
        System.out.println("Enter your new enquiry:");
        String message = sc.nextLine();
        System.out.print("Confirm edit (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            enquiry.edit(message);
        }
        else {
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
        System.out.println("Select enquiry number: ");
        int number = sc.nextInt();
        sc.nextLine();
        Enquiry enquiry = enquiryList.get(number-1);
        if (!enquiry.getAnswer().equals("No reply yet")) {
            System.out.println("Enquiry already has a reply. Unable to delete.");
            return;
        }
        System.out.print("Confirm removal (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            enquiryList.remove(enquiry);
            applicant.getApplication().getProject().removeEnquiry(enquiry);
        }
        else {
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

}