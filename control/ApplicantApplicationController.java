/**
 * Handles all application-related operations for applicants.
 *
 * This includes applying for a BTO project, viewing application status,making a booking request, and requesting application withdrawal.
 *
 */

package control;

import boundary.ApplicationViewer;
import boundary.ProjectViewer;
import entity.*;
import repository.ApplicationService;
import java.util.Scanner;
import utils.*;

public class ApplicantApplicationController implements IApplicantApplicationService{
    private static Scanner sc = new Scanner(System.in);

    /**
     * Allows the applicant to apply for a project they are eligible for.
     *
     * Prompts the user to select a project and flat type (if they are married), validates input,
     * and creates the application.
     *
     * There are also checks for whether the applicant has a pending application,
     * if they're reapplying for the same project they were unsuccessful for,
     * and for flat availability before allowing the application.
     *
     * @param applicant The applicant who is applying for a project.
     * @param projectService The project service used to retrieve list of projects to apply for
     */
    public void applyForProject(Applicant applicant, IApplicantProjectService projectService) {
        Application previousApplication = applicant.getApplication();

        if (previousApplication != null) {
            Status status = previousApplication.getStatus();

            // Checks if user has a previous application, and if that application is still pending
            if (status != Status.UNSUCCESSFUL) {
                System.out.println("You have already applied for a project.");
                return;
            }
        }

        projectService.viewOpenProjects(applicant);

        Project project;
        while (true) {
            System.out.print("Please enter the name of the project you'd like to apply for: ");
            String inputTitle = sc.nextLine();
            project = projectService.findProjectByName(applicant, inputTitle);

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
        ProjectViewer.printOneProject(project, Role.APPLICANT, applicant);

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
            ApplicationService.addApplication(application);
            ApplicationService.updateApplications();
            project.addApplication(application);
            System.out.println("Application submitted.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    /**
     * Displays the details of the applicant's current application.
     *
     * @param applicant The applicant whose application is to be displayed.
     */
    public void viewApplication(Applicant applicant) {
        Application application = applicant.getApplication();
        if (application != null) {
            ApplicationViewer.printApplication(application);
        } else {
            System.out.println("No application submitted.");
        }
    }

    /**
     * Allows the applicant to request a flat booking if their application was successful.
     *
     * @param applicant The applicant requesting to book a flat.
     */
    public void requestBooking(Applicant applicant) {
        Application application = applicant.getApplication();
        if (application == null) {
            System.out.println("No existing application.");
            return;
        }

        ApplicationViewer.printApplication(application);
        if (InputHelper.confirm("Are you sure you want to book a flat for this project?")) {
            if (application.getStatus() == Status.SUCCESSFUL && !application.isBookingRequested()) {
                application.setBookingRequested(true);
                ApplicationService.updateApplications();
                System.out.println("Booking request has been sent to the HDB Officers.");
            } else if (application.isBookingRequested()) {
                System.out.println("You've already made a booking request for this project.");
            }
            else {
                System.out.println("Booking not available right now. Please check your application status.");
            }
        } else {
            System.out.println("Request cancelled.");
        }
    }

    /**
     * Allows the applicant to request a withdrawal of their current application.
     *
     * @param applicant The applicant requesting withdrawal.
     */
    public void requestWithdrawal(Applicant applicant) {
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
                ApplicationService.updateApplications();
                System.out.println("Withdrawal request has been sent.");
            }
        } else {
            System.out.println("Request cancelled.");
        }
    }
}
