/**
 * This class that allows managers to create, edit, delete, toggle visibility,
 * and view BTO projects.
 */

package control;

import boundary.ProjectFilterMenu;
import boundary.ProjectViewer;
import entity.*;
import java.util.*;
import repository.ProjectService;
import utils.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HDBManagerProjectController {
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Allows a manager to create a new BTO project, ensuring no date overlaps with existing projects.
     * Prompts for input details such as name, neighborhood, flat counts/prices, and application dates.
     * Adds the project to the manager's list and saves it via ProjectService if confirmed.
     *
     * @param manager      The manager creating the project.
     */
    public static void createProject(HDBManager manager) {
        LocalDate openDate = InputHelper.readDate("Enter application opening date (DD/MM/YYYY): ", formatter);
        LocalDate closeDate = InputHelper.readDate("Enter application closing date (DD/MM/YYYY): ", formatter);
        if (closeDate.isBefore(openDate)) {
            System.out.println("Application closing date cannot be earlier than opening date.");
            return;
        }

        // TEMP Project just for overlap checking
        Project temp = new Project("", "", 0, 0, 0, 0, openDate, closeDate, manager, 0, new ArrayList<>());

        for (Project existing : manager.getCreatedProjects()) {
            if (DateOverlap.applicationPeriodsOverlap(existing, temp)) {
                System.out.println("You are already managing a project that overlaps with this application period.");
                System.out.println("Cannot create project.");
                return;
            }
        }

        // Proceed with the rest of the inputs
        System.out.print("Enter project name: ");
        String name = sc.nextLine();
        System.out.print("Enter neighborhood: ");
        String neighborhood = sc.nextLine();
        int twoRooms = InputHelper.readInt("Enter the number of 2-room units: ");
        int threeRooms = InputHelper.readInt("Enter the number of 3-room units: ");
        float twoRoomsPrice = InputHelper.readFloat("Enter 2-room price: $");
        float threeRoomsPrice = InputHelper.readFloat("Enter 3-room price: $");
        int officerSlots;
        while (true) {
            officerSlots = InputHelper.readInt("Enter the number of officer slots (max 10): ");
            if (officerSlots >= 1 && officerSlots <= 10) break;
            System.out.println("Please enter a number between 1 and 10.");
        }

        Project p = new Project(name, neighborhood, twoRooms, twoRoomsPrice, threeRooms, threeRoomsPrice, openDate, closeDate, manager, officerSlots, new ArrayList<>());

        ClearPage.clearPage();
        ProjectViewer.printOneProject(p, manager);
        if (InputHelper.confirm("Confirm project creation?")) {
            ProjectService.addProject(p);
            ProjectService.updateProjects();
            manager.addCreatedProject(p);
            System.out.println("Project created.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    /**
     * Enables a manager to edit a selected project they have created.
     * Prompts for each editable field and confirms the update.
     * Prevents edits that cause overlapping application dates with other projects.
     *
     * @param manager The manager editing their project.
     */
    public static void editProject(HDBManager manager) {
        System.out.println("Here are the list of projects:");
        List<Project> createdProjects = manager.getCreatedProjects();
        ProjectViewer.printProjects(createdProjects, manager);

        System.out.print("Enter the name of the project you'd like to edit: ");
        String inputTitle = sc.nextLine();
        Project original = createdProjects.stream()
                .filter(p -> p.getProjectName().equalsIgnoreCase(inputTitle) && p.getManager().equals(manager))
                .findFirst()
                .orElse(null);

        if (original == null) {
            System.out.println("Project not found.");
            return;
        }

        // Copy original values into temporary variables
        String neighborhood = original.getNeighborhood();
        int twoRooms = original.getNumRoom(FlatType.TWOROOMS);
        int threeRooms = original.getNumRoom(FlatType.THREEROOMS);
        float price2 = original.getSellingPrice(FlatType.TWOROOMS);
        float price3 = original.getSellingPrice(FlatType.THREEROOMS);
        int officerSlots = original.getOfficerSlot();
        LocalDate openDate = original.getOpenDate();
        LocalDate closeDate = original.getCloseDate();
        int currentAssigned = original.getOfficerSlotList().size();

        // Start editing into temp variables
        ClearPage.clearPage();
        ProjectViewer.printOneProject(original, manager);
        System.out.println("\nEnter new values (press Enter to keep current value):");

        System.out.print("New neighborhood: ");
        String input = sc.nextLine();
        if (!input.isEmpty()) neighborhood = input;

        System.out.print("New 2-room unit count: ");
        input = sc.nextLine();
        if (!input.isEmpty()) {
            try {
                twoRooms = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping original value.");
            }
        }

        System.out.print("New 3-room unit count: ");
        input = sc.nextLine();
        if (!input.isEmpty()) {
            try {
                threeRooms = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping original value.");
            }
        }

        System.out.print("New 2-room price: $");
        input = sc.nextLine();
        if (!input.isEmpty()) {
            try {
                price2 = Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping original value.");
            }
        }

        System.out.print("New 3-room price: $");
        input = sc.nextLine();
        if (!input.isEmpty()) {
            try {
                price3 = Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping original value.");
            }
        }

        while (true) {
            System.out.print("New officer slots: ");
            input = sc.nextLine();
            if (input.isEmpty()) break;
            try {
                int newSlots = Integer.parseInt(input);
                if (newSlots < currentAssigned) {
                    System.out.printf("You currently have %d officer(s) assigned. You cannot set slots below that.\n", currentAssigned);
                } else if (newSlots > 10) {
                    System.out.println("Maximum number of slots is 10.");
                } else {
                    officerSlots = newSlots;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }

        System.out.print("New opening date (dd/MM/yy): ");
        input = sc.nextLine();
        if (!input.isEmpty()) {
            try {
                openDate = LocalDate.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yy.");
                return;
            }
        }

        System.out.print("New closing date (dd/MM/yy): ");
        input = sc.nextLine();
        if (!input.isEmpty()) {
            try {
                closeDate = LocalDate.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yy.");
                return;
            }
        }

        // Check overlap against manager’s other projects
        for (Project other : createdProjects) {
            if (!other.equals(original)) {
                Project temp = new Project("", "", 0, 0, 0, 0, openDate, closeDate, manager, 0, new ArrayList<>());
                if (DateOverlap.applicationPeriodsOverlap(other, temp)) {
                    System.out.println("Edited dates overlap with another project you're managing.");
                    return;
                }
            }
        }

        // Show edited preview
        Project preview = new Project(original.getProjectName(), neighborhood, twoRooms, price2, threeRooms, price3, openDate, closeDate, manager, officerSlots, original.getOfficerSlotList());

        ClearPage.clearPage();
        System.out.println("Updated project preview:");
        ProjectViewer.printOneProject(preview, manager);

        if (InputHelper.confirm("Confirm changes to this project")) {
            // Apply final changes to original project
            original.setNeighborhood(neighborhood);
            original.setNum2Room(twoRooms);
            original.setNum3Room(threeRooms);
            original.setSellingPrice2Room(price2);
            original.setSellingPrice3Room(price3);
            original.setOfficerSlots(officerSlots);
            original.setOpenDate(openDate);
            original.setCloseDate(closeDate);
            ProjectService.updateProjects();
            System.out.println("Project updated successfully.");
        } else {
            System.out.println("Changes discarded.");
        }
    }

    public static void deleteProject(HDBManager manager, List<Project> allProjects) {
        List<Project> createdProjects = manager.getCreatedProjects();
        ProjectViewer.printProjects(createdProjects, manager);
        System.out.print("Enter the name of the project you'd like to delete: ");
        String inputTitle = sc.nextLine();
        Project project = createdProjects.stream()
                .filter(p -> inputTitle.equalsIgnoreCase(p.getProjectName()))
                .findFirst()
                .orElse(null);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, manager);
        if (InputHelper.confirm("Confirm deletion of project")) {
            createdProjects.remove(project);
            ProjectService.removeProject(project);
            ProjectService.updateProjects();
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Request cancelled.");
        }
    }

    public static void toggleProjectVisibility(HDBManager manager) {
        List<Project> createdProjects = manager.getCreatedProjects();
        System.out.println("Here are the list of projects:");
        ProjectViewer.printProjects(createdProjects, manager);
        System.out.print("Enter the name of the project you'd like to toggle the visibility for: ");
        String inputTitle = sc.nextLine();
        Project project = createdProjects.stream()
                .filter(p -> inputTitle.equalsIgnoreCase(p.getProjectName()))
                .findFirst()
                .orElse(null);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        ClearPage.clearPage();
        ProjectViewer.printOneProject(project, manager);
        System.out.println("Current visibility: " + (project.isVisible() ? "Visible" : "Hidden"));
        if (InputHelper.confirm("Would you like to toggle this project's visibility")) {
            project.setVisibility(!project.isVisible());
            ProjectService.updateProjects();
            System.out.println("Project visibility toggled.");
            System.out.println("New visibility: " + (project.isVisible() ? "Visible" : "Hidden"));
        } else {
            System.out.println("Action cancelled.");
        }
    }

    /**
     * Displays a list of either the manager’s own projects or all available projects.
     * Uses {@link ProjectFilterMenu} to allow viewing with filtering options.
     *
     * @param manager     The manager initiating the view.
     * @param allProjects All available projects in the system.
     * @param session     The current user session.
     */
    public static void viewAllProjects(HDBManager manager, List<Project> allProjects, UserSession session) {
        // Filter for own projects
        System.out.println("Would you like to:");
        System.out.println("1. View only your own created projects");
        System.out.println("2. View all projects");
        int viewChoice;
        while (true) {
            viewChoice = InputHelper.readInt("Enter choice (1 or 2): ");
            if (viewChoice == 1 || viewChoice == 2) break;
            System.out.println("Please enter 1 or 2.");
        }

        List<Project> projectsToShow = (viewChoice == 1)
                ? manager.getCreatedProjects()
                : allProjects;

        if (projectsToShow.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }

        ClearPage.clearPage();
        System.out.println("Here are the list of projects:");
        ProjectFilterMenu.viewFilteredProjects(session, projectsToShow);
    }
}
