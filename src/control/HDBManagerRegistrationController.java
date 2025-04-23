/**
 * This class allows managers to process registrations for projects made by officers.
 */

package control;

import entity.*;
import repository.ProjectService;
import repository.RegistrationService;
import utils.InputHelper;
import utils.BoxPrinter;

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
     * Returns all registrations from the manager's created projects filtered by status.
     *
     * @param manager The manager whose project registrations are to be retrieved.
     * @param filterStatus The status to filter by (null for all).
     * @return List of registrations matching the filter.
     */
    private List<Registration> getFilteredRegistrations(HDBManager manager, Status filterStatus) {
        List<Registration> filteredList = new ArrayList<>();
        for (Project project : manager.getCreatedProjects()) {
            for (Registration reg : project.getRegistrations()) {
                if (filterStatus == null || reg.getStatus() == filterStatus) {
                    filteredList.add(reg);
                }
            }
        }
        return filteredList;
    }

    /**
     * Filters pending registrations where the officer is unassigned or their assigned project is expired.
     *
     * @param manager The manager whose project registrations are being processed.
     * @return List of eligible pending registrations.
     */
    private List<Registration> getPendingRegistrationsForProcessing(HDBManager manager) {
        List<Registration> filteredList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Project project : manager.getCreatedProjects()) {
            for (Registration reg : project.getRegistrations()) {
                HDBOfficer officer = reg.getRegisteredOfficer();
                boolean isUnassignedOrExpired =
                        officer.getAssignedProject() == null ||
                                officer.getAssignedProject().getCloseDate().isBefore(today);

                if (isUnassignedOrExpired && reg.getStatus() == Status.PENDING) {
                    filteredList.add(reg);
                }
            }
        }
        return filteredList;
    }

    /**
     * Prints a formatted list of registrations with masked NRICs and project info.
     *
     * @param registrations The list of registrations to display.
     */
    private static void printRegistrations(List<Registration> registrations) {
        for (int i = 0; i < registrations.size(); i++) {
            Registration reg = registrations.get(i);
            HDBOfficer officer = reg.getRegisteredOfficer();
            String maskedNric = "****" + officer.getNric().substring(officer.getNric().length() - 4);
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
     * Allows the manager to approve or reject pending officer registrations.
     *
     * @param manager The manager processing the registrations.
     */
    public void processRegistrations(HDBManager manager) {
        List<Registration> pendingList = getPendingRegistrationsForProcessing(manager);

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
     * Processes the selected registration (approve or reject) and updates the system.
     *
     * @param project The project the officer is registering for.
     * @param officer The officer applying for registration.
     * @param selectedReg The registration object.
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
