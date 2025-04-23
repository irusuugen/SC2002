/**
 * Handles all registration-related operations for HDB Managers, such as viewing and processing officer registrations.
 */

package control;

import entity.*;
import repository.ProjectService;
import repository.RegistrationService;
import utils.*;

import java.time.LocalDate;
import java.util.*;

public class HDBManagerRegistrationController implements IManagerRegistrationService {

    /**
     * Displays officer registrations filtered by status (Pending, Approved, Rejected, or All).
     * Registrations associated with the projects the manager is handling are shown.
     *
     * @param manager The HDB Manager viewing the registrations.
     */
    public void viewRegistrations(HDBManager manager) {
        System.out.println("""
        View Officer Registrations:
        1. Pending
        2. Approved
        3. Rejected
        4. All
        """);

        int choice;
        while (true) {
            choice = InputHelper.readInt("Select an option: ");
            if (choice >= 1 && choice <= 4) break;
            System.out.println("Enter a number between 1 and 4.");
        }

        Status filterStatus = null;
        if (choice != 4) {
            filterStatus = switch (choice) {
                case 1 -> Status.PENDING;
                case 2 -> Status.APPROVED;
                case 3 -> Status.REJECTED;
                default -> null;
            };
        }

        List<Registration> filteredList = getFilteredRegistrations(manager, filterStatus);

        if (filteredList.isEmpty()) {
            System.out.println("No registrations found for the selected status.");
            return;
        }

        printRegistrations(filteredList);
    }

    /**
     * Filters and returns a list of registrations for the given manager and status.
     * Registrations are only included if their associated project is within an appliation
     * period, and the officer is not already assigned.
     *
     * @param manager       The HDB Manager whose projects to check.
     * @param filterStatus  The registration status to filter by (can be null for no filtering).
     * @return A list of filtered registrations.
     */
     private List<Registration> getFilteredRegistrations(HDBManager manager, Status filterStatus) {
        List<Registration> filteredList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Project project : manager.getCreatedProjects()) {
            for (Registration reg : project.getRegistrations()) {
                HDBOfficer officer = reg.getRegisteredOfficer();
                if ((officer.getAssignedProject() == null || officer.getAssignedProject().getCloseDate().isBefore(today)) && (filterStatus == null || reg.getStatus() == filterStatus)) {
                    filteredList.add(reg);
                }
            }
        }
        return filteredList;
    }

    /**
     * Prints a formatted list of registrations with masked NRICs and project information.
     *
     * @param registrations The list of registrations to display.
     */
    private static void printRegistrations(List<Registration> registrations) {
        for (int i = 0; i < registrations.size(); i++) {
            Registration reg = registrations.get(i);
            HDBOfficer officer = reg.getRegisteredOfficer();
            String nric = officer.getNric();
            String maskedNric = "****" + nric.substring(nric.length() - 4);
            String nameWithMaskedNric = officer.getName() + " (" + maskedNric + ")";

            BoxPrinter.printTopBorder();
            BoxPrinter.printRow("Registration No.:", String.valueOf(i + 1));
            BoxPrinter.printRow("Officer", nameWithMaskedNric);
            BoxPrinter.printRow("Project Name", reg.getProject().getProjectName());
            BoxPrinter.printRow("Registration Status", reg.getStatus().toString());
            BoxPrinter.printBottomBorder();
        }
    }

    /**
     * Processes pending officer registrations for the manager's projects.
     * The user can choose to approve or reject a selected pending registration.
     * Upon approval, the officer is added to the project's officer list if a slot is available.
     *
     * @param manager The HDB Manager responsible for processing registrations.
     */
    public void processRegistrations(HDBManager manager) {
        List<Registration> pendingList = getFilteredRegistrations(manager, Status.PENDING);

        if (pendingList.isEmpty()) {
            System.out.println("There are no pending officer registrations to process.");
            return;
        }

        printRegistrations(pendingList);

        int selection = -1;
        while (selection < 1 || selection > pendingList.size()) {
            selection = InputHelper.readInt("Select a registration to process (1 - " + pendingList.size() + "): ");
        }

        Registration selectedReg = pendingList.get(selection - 1);
        Project project = selectedReg.getProject();
        HDBOfficer officer = selectedReg.getRegisteredOfficer();

        processRegistrationDecision(project, officer, selectedReg);
    }

    /**
     * Handles the decision process for a selected registration.
     * The manager can approve or reject the registration. If approved and the project
     * has capacity, the officer is added to the project.
     *
     * @param project       The project associated with the registration.
     * @param officer       The officer being registered.
     * @param selectedReg   The registration to process.
     */
    private static void processRegistrationDecision(Project project, HDBOfficer officer, Registration selectedReg) {
        System.out.println("1. Approve");
        System.out.println("2. Reject");

        int decision = -1;
        while (decision != 1 && decision != 2) {
            decision = InputHelper.readInt("Enter your decision: ");
        }

        String action = (decision == 1) ? "approve" : "reject";
        boolean confirmed = InputHelper.confirm("Are you sure you want to " + action + " this registration?");

        if (!confirmed) {
            System.out.println("Action cancelled. Returning to registration list.");
            return;
        }

        if (decision == 1) {
            if (project.hasAvailableOfficerSlot()) {
                selectedReg.setStatus(Status.APPROVED);
                RegistrationService.updateRegistrations();
                project.addOfficer(officer);
                ProjectService.updateProjects();
                officer.setAssignedProject(project);
                System.out.println("Officer registration approved.");
            } else {
                System.out.println("Approval failed. No officer slots available in this project.");
            }
        } else {
            selectedReg.setStatus(Status.REJECTED);
            RegistrationService.updateRegistrations();
            System.out.println("Officer registration rejected.");
        }

        System.out.println("Registration processed.");
    }

}
