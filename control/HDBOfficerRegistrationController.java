package control;

import java.time.LocalDate;
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
			boolean isAlreadyAssigned = officer.getAssignedProject() != null && officer.getAssignedProject().equals(project);

			// Check if the officer has already registered for this project
			boolean hasAlreadyRegistered = officer.getRegistrationList().stream()
					.anyMatch(reg -> reg.getProject().equals(project));

			boolean hasDateClash = officer.getAssignedProject() != null && DateOverlap.applicationPeriodsOverlap(officer.getAssignedProject(), project);

			LocalDate today = LocalDate.now();
			
			boolean isClosed = project.getCloseDate().isBefore(today);

			if (!isAlreadyAssigned && !hasAlreadyRegistered && !hasDateClash && !isClosed) {
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
		if(officer.getAssignedProject() != null && officer.getAssignedProject().checkOpeningPeriod()){
			System.out.println("You are currently an officer of an active project");
		}
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
				RegistrationService.addRegistration(registration);
				RegistrationService.updateRegistrations();
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

	public static void printAssignedProject(HDBOfficer officer){
		if(officer.getAssignedProject() == null){
			System.out.println("You are not assigned to any projects.");
			return;
		}
		ProjectViewer.printOneProject(officer.getAssignedProject(), officer);
	}
}