package control;

import boundary.ApplicationViewer;
import boundary.ProjectViewer;
import entity.*;
import java.util.Scanner;
import utils.*;

public class ApplicantApplicationController {
    private static Scanner sc = new Scanner(System.in);

    // Creates an application
    public static void applyForProject(Applicant applicant) {
        Application previousApplication = applicant.getApplication();

        if (previousApplication != null) {
            Status status = previousApplication.getStatus();
            Project prevProject = previousApplication.getProject();

            // Block if the status is neither UNSUCCESSFUL nor WITHDRAWN
            if (status != Status.UNSUCCESSFUL) {
                System.out.println("You have already applied for a project.");
                return;
            }
        }

        ApplicantProjectController.viewOpenProjects(applicant);

        Project project;
        while (true) {
            System.out.print("Please enter the name of the project you'd like to apply for: ");
            String inputTitle = sc.nextLine();
            project = findProjectByName(applicant, inputTitle);

            if (project == null) {
                if (!InputHelper.confirm("Project not found. Retry?")) return;
            } else {
                // Check if trying to reapply for the same project
                if (previousApplication != null && project.equals(previousApplication.getProject())) {
                    System.out.println("You cannot reapply for the same project you were previously rejected or withdrew from.");
                    return;
                }
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
            if (application.isWithdrawalRequested()) {
                System.out.println("A withdrawal request has already been made.");
            } else {
                application.setWithdrawalRequested(true);
                System.out.println("Withdrawal request has been sent.");
            }
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static Project findProjectByName(Applicant applicant, String name) {
        return ApplicantProjectController.getOpenProjects(applicant).stream()
                .filter(p -> name.equalsIgnoreCase(p.getProjectName()))
                .findFirst()
                .orElse(null);
    }
}
