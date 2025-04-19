package control;

import java.util.*;
import boundary.ProjectViewer;
import entity.*;
import repository.*;
import utils.*;


public class HDBOfficerRegistrationController{
	
	private static Scanner sc = new Scanner(System.in);

	public static List<Project> getRegistrableProjects(HDBOfficer officer) {
		List<Project> registrableProjects = new ArrayList<>();

		for (Project project : ProjectService.getAllProjects()) {
			boolean isAlreadyAssigned = officer.getAssignedProjects().contains(project);
			boolean hasAppliedToThisProject = officer.getApplication() != null &&
					project.equals(officer.getApplication().getProject());

			// Check for overlap in application periods with any already assigned project
			boolean hasDateClash = officer.getAssignedProjects().stream()
					.anyMatch(assigned -> DateOverlap.applicationPeriodsOverlap(assigned, project));

			if (!isAlreadyAssigned && !hasAppliedToThisProject && !hasDateClash) {
				registrableProjects.add(project);
			}
		}

		return registrableProjects;
	}

	public static void viewRegistrableProjects(HDBOfficer officer)
	{
		List<Project> registrableProjects = getRegistrableProjects(officer);
		ProjectViewer.printProjects(registrableProjects, officer);
	}

	public static void registerForProject(HDBOfficer officer) {
		viewRegistrableProjects(officer);
		Project project;
			// Loop until a valid project is selected
		while (true) {
			System.out.println("Please enter the name of the project you'd like to register for: ");
			String projectName = sc.nextLine();
			project = getRegistrableProjects(officer).stream()
					.filter(p -> projectName.equals(p.getProjectName()))
					.findFirst()
					.orElse(null);

			// If the project was not found, ask for retry
			if (project == null) {
				System.out.print("Project was not found. Retry? (Y/N): ");
				String retry = sc.nextLine();
				if (!retry.equalsIgnoreCase("Y")) {
					return; // Exit if the user doesn't want to retry
				}
			} else {
				break; // Exit the inner loop once a valid project is found
			}
		}

		// Create a new Registration and register the officer
		while (true) {
			System.out.print("Are you sure you want to apply for this project? (Y/N): ");
			String choice = sc.nextLine();
			if (choice.equalsIgnoreCase("Y")) {
				Registration registration = new Registration(officer, project);
				project.registerOfficer(registration);
				officer.addRegistration(registration);
				System.out.println("Registered successfully!");
				break;
			}
			else if (choice.equalsIgnoreCase("N")) {
				System.out.println("Request cancelled.");
				return;
			} else {
				System.out.println("Invalid input. Try again.");
			}
		}
	}


	public static void viewRegistrations(HDBOfficer officer)
	{
		List<Registration> registrationList = officer.getRegistrationList();

		if (registrationList.isEmpty()) {
			System.out.println("You have not registered for any projects.");
			return;
		}

		System.out.println("Your project registrations:");
		BoxPrinter.printTopBorder();
		for (int i = 0; i < registrationList.size(); i++) {
			Registration registration = registrationList.get(i);
			BoxPrinter.printRow("Project Name: ", registration.getProject().getProjectName());
			BoxPrinter.printRow("Registration Status", registration.getStatus().toString());
			if (i < registrationList.size() - 1) {
				BoxPrinter.printDivider();
			} else {
				BoxPrinter.printBottomBorder();
			}
		}
	}
}
