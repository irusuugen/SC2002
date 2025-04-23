package control;

import entity.*;
import utils.*;

import java.util.*;

public class HDBManagerRegistrationController {

    public static void viewRegistrations(HDBManager manager) {
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

    private static List<Registration> getFilteredRegistrations(HDBManager manager, Status filterStatus) {
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

    public static void processRegistrations(HDBManager manager) {
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

    private static void processRegistrationDecision(Project project, HDBOfficer officer, Registration selectedReg) {
        System.out.println("1. Approve");
        System.out.println("2. Reject");

        int decision = -1;
        while (decision != 1 && decision != 2) {
            decision = InputHelper.readInt("Enter your decision: ");
        }

        if (decision == 1) {
            if (project.hasAvailableOfficerSlot()) {
                selectedReg.setStatus(Status.APPROVED); // Set status first
                project.addOfficer(officer);
                officer.addAssignedProject(project);
                System.out.println("Officer registration approved.");
            } else {
                System.out.println("Approval failed. No officer slots available in this project.");
                return;
            }
        } else {
            selectedReg.setStatus(Status.REJECTED); // Set status first
            System.out.println("Officer registration rejected.");
        }


        System.out.println("Registration processed.");
    }
}
