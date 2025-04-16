package control;

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
        if (applicant.isMarried() && applicant.getAge() >= 21) {
            for (Project project : ProjectService.getAllProjects()) {
                if (project.getVisibility()) {
                    openProjects.add(project);
                }
            }
        } else if (!applicant.isMarried() && applicant.getAge() >= 35) {
            for (Project project : ProjectService.getAllProjects()) {
                if (project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 && project.getVisibility()) {
                    openProjects.add(project);
                }
            }
        }
        return openProjects;
    }

    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects);
    }

    public static boolean applyForProject(Applicant applicant) {
        if (applicant.getApplication() != null) {
            System.out.println("You have already registered for a project.");
            return false;
        }
        viewOpenProjects(applicant);
        System.out.println("Please enter the name of the project you'd like to apply for: ");
        String inputTitle = sc.nextLine();
        Project project = getOpenProjects(applicant).stream()
            .filter(p -> inputTitle.equals(p.getProjectName()))
            .findFirst()
            .orElse(null);
        System.out.println("Enter the flat type you'd like (2 or 3): ");
        int flatType = sc.nextInt();
        sc.nextLine();
        ClearPage.clearPage();
        ProjectViewer.printOneProject(project);
        System.out.print("Are you sure you want to apply for this project? (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            if (flatType==2) {
            applicant.setApplication(new Application(project, FlatType.TWOROOMS, applicant));
            } else if (flatType == 3) {
                applicant.setApplication(new Application(project, FlatType.TWOROOMS, applicant));
            } 
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
                ProjectViewer.printOneProject(application.getProject());
        } else {
            System.out.println("No application submitted.");
        }
    }

    // public boolean withdrawApplication() {
    //     if (this.application == null) return false; // Checks for existing application
    //     application.withdraw();
    //     this.application = null;
    //     return true;
    // }

    public static void submitEnquiry(Applicant applicant) {
        viewOpenProjects(applicant);
        System.out.print("Enter the name of the project you'd like to submit an enquiry for: ");
        String inputTitle = sc.nextLine();
        Project project = getOpenProjects(applicant).stream()
            .filter(p -> inputTitle.equals(p.getProjectName()))
            .findFirst()
            .orElse(null);
        ClearPage.clearPage();
        ProjectViewer.printOneProject(project);
        System.out.println("Enter your enquiry:");
        String message = sc.nextLine();
        System.out.println("Your enquiry is: " + message);
        System.out.print("Confirm submission (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            applicant.addEnquiry(new Enquiry(message, project));
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
        System.out.println("Select enquiry via index: ");
        int index = sc.nextInt();
        sc.nextLine();
        Enquiry enquiry = applicant.getEnquiries().get(index);
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
        System.out.println("Select enquiry via index: ");
        int index = sc.nextInt();
        sc.nextLine();
        Enquiry enquiry = enquiryList.get(index);
        System.out.print("Confirm removal (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            enquiryList.remove(enquiry);
        }
        else {
            System.out.println("Request cancelled.");
        }
    }

    public static void viewEnquiries(Applicant applicant) {
        int index = 0;
        for (Enquiry enquiry : applicant.getEnquiries()) {
            System.out.println("Enquiry Index: " + index);
            System.out.println(enquiry);
        }
    }

}