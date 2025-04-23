/**
 * Handles all application-related operations for managers
 *
 * This includes processing applicants' applications,
 * processing applicants' withdrawal requests,
 * retrieving applications for the manager's created projects.
 *
 */

package control;

import entity.*;
import repository.ApplicationService;
import repository.ProjectService;

import java.util.*;
import utils.*;
import boundary.*;

public class HDBManagerApplicationController implements IManagerApplicationService{

    /**
     * Retrieves a list of applications for the projects the manager
     * is handling that are still pending and not withdrawn,
     * and allows the manager to select an application to approve or reject.
     *
     * @param manager The manager processing the applications
     *
     */
    public void processApplication(HDBManager manager) {
        List<Application> applications = getAllApplications(manager).stream()
                .filter(app -> app.getStatus() == Status.PENDING && !app.isWithdrawalRequested())
                .toList();

        if (applications.isEmpty()) {
            System.out.println("There are no pending applications for your projects.");
            return;
        }

        System.out.println("Pending applications for your created projects:");
        for (int i = 0; i < applications.size(); i++) {
            ApplicationViewer.printApplications(applications);
        }

        int selection = -1;
        while (selection < 1 || selection > applications.size()) {
            selection = InputHelper.readInt("Select an application to process (1 - " + applications.size() + "): ");
        }

        Application selectedApp = applications.get(selection - 1);
        Project project = selectedApp.getProject();

        // Show application again before decision
        ClearPage.clearPage();
        System.out.println("\nSelected Application:");
        ApplicationViewer.printApplication(selectedApp);

        int decision = InputHelper.readInt("1. Approve Application\n2. Reject Application\nEnter your decision: ");
        while (decision != 1 && decision != 2) {
            decision = InputHelper.readInt("Invalid choice. Enter 1 to approve or 2 to reject: ");
        }

        if (decision == 1) {
            boolean confirmed = InputHelper.confirm("Are you sure you want to approve this application?");
            if (!confirmed) {
                System.out.println("Approval cancelled.");
                return;
            }
            processApproval(selectedApp, project);
        } else {
            boolean confirmed = InputHelper.confirm("Are you sure you want to reject this application?");
            if (!confirmed) {
                System.out.println("Rejection cancelled.");
                return;
            }
            selectedApp.markUnsuccessful();
            ApplicationService.updateApplications();
            System.out.println("Application rejected.");
        }
        
    }

    /**
     * Changes the application status and updates the information in the application list
     *
     * @param selectedApp The application that the manager has chosen to process
     * @param project The project for which the application was made
     *
     */
    private static void processApproval(Application selectedApp, Project project) {
        FlatType type = selectedApp.getFlatType();
        if (project.getNumFlatAvailable(type) > 0) {
            selectedApp.markSuccessful();
            ApplicationService.updateApplications();
            ProjectService.updateProjects();
            System.out.println("Application approved.");
        } else {
            selectedApp.markUnsuccessful();
            ApplicationService.updateApplications();
            System.out.println("No available flats. Application marked as unsuccessful.");
        }
    }

    /**
     * Retrieves all applications for the projects the manager is handling
     *
     * @param manager The manager who is retrieving their applications
     * @return List of {@link Application} objects associated with the projects that the manager is handling
     *
     */
    public static List<Application> getAllApplications(HDBManager manager) {
        List<Application> ret = new ArrayList<>();
        for (Project p : manager.getCreatedProjects()) {
            ret.addAll(p.getApplications());
        }
        return ret;
    }

    /**
     *
     *
     * @param manager The manager who is processing the withdrawals
     *
     */
    public void processWithdrawal(HDBManager manager) {
        List<Application> withdrawalRequests = new ArrayList<>();

        // Collect all withdrawal requests
        for (Application app : getAllApplications(manager)) {
            if (app.isWithdrawalRequested() && (app.getStatus() == Status.PENDING || app.getStatus() == Status.SUCCESSFUL || app.getStatus() == Status.BOOKED)) {
                withdrawalRequests.add(app);
            }
        }

        if (withdrawalRequests.isEmpty()) {
            System.out.println("No withdrawal requests found.");
            return;
        }

        // Display all withdrawal requests
        displayWithdrawalRequests(withdrawalRequests);

        int selection = -1;
        while (selection < 1 || selection > withdrawalRequests.size()) {
            selection = InputHelper.readInt("Select a withdrawal request to process (1 - " + withdrawalRequests.size() + "): ");
        }

        Application selectedApp = withdrawalRequests.get(selection - 1);
        Project project = selectedApp.getProject();

        // Ask if they want to approve or reject
        processWithdrawalDecision(selectedApp, project);
    }

    /**
     * Prints the list of withdrawal requests in the box format
     *
     * @param withdrawalRequests The list of withdrawal requests
     *
     */
    private static void displayWithdrawalRequests(List<Application> withdrawalRequests) {
        System.out.println("Withdrawal Requests:");
        for (int i = 0; i < withdrawalRequests.size(); i++) {
            Application app = withdrawalRequests.get(i);
            String nric = app.getApplicant().getNric();
            String maskedNric = "****" + nric.substring(nric.length() - 4);
            String nameWithMaskedNric = app.getApplicant().getName() + " (" + maskedNric + ")";

            BoxPrinter.printTopBorder();
            BoxPrinter.printRow("Request No.:", String.valueOf(i + 1));
            BoxPrinter.printRow("Applicant", nameWithMaskedNric);
            BoxPrinter.printRow("Project Name", app.getProject().getProjectName());
            BoxPrinter.printRow("Flat Type", app.getFlatType().toString());
            BoxPrinter.printRow("Status", app.getStatus().toString());
            BoxPrinter.printBottomBorder();
        }
    }

    /**
     * Asks the manager whether they want to approve or reject the withdrawal request.
     * Changes the application status and adds back the occupied flat to the number of available flats if
     * the application status was BOOKED.
     *
     * @param selectedApp The application selected with a withdrawal request
     * @param project The project for which the application was made for with a withdrawal request
     *
     */
    private static void processWithdrawalDecision(Application selectedApp, Project project) {
        int decision = InputHelper.readInt("1. Approve\n2. Reject\nEnter your decision: ");
        while (decision != 1 && decision != 2) {
            decision = InputHelper.readInt("Invalid choice. Enter 1 to approve or 2 to reject: ");
        }

        if (decision == 1) {
            boolean confirmed = InputHelper.confirm("Are you sure you want to approve this withdrawal?");
            if (!confirmed) {
                System.out.println("Withdrawal approval cancelled.");
                return;
            }

            selectedApp.withdraw();
            ApplicationService.updateApplications();
            if (selectedApp.getStatus() == Status.BOOKED) {
                project.removeOccupiedFlat(selectedApp.getFlatType());
                ProjectService.updateProjects();
            }
            System.out.println("Withdrawal approved.");
        } else {
            boolean confirmed = InputHelper.confirm("Are you sure you want to reject this withdrawal?");
            if (!confirmed) {
                System.out.println("Withdrawal rejection cancelled.");
                return;
            }
            System.out.println("Withdrawal rejected.");
        }
    }
}
