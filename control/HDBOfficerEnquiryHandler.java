package control;

import java.util.*;

import boundary.EnquiriesViewer;
import entity.*;
import repository.EnquiryService;
import utils.*;

public class HDBOfficerEnquiryHandler{
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void viewAssignedProjectsEnquiries(HDBOfficer officer)
	{
		Project selectedProject = selectProject(officer.getAssignedProjects());
		if (selectedProject == null) {
			return;
		}
		List<Enquiry> enquiries = selectedProject.getEnquiries();
		if (enquiries.isEmpty())
		{
			System.out.println("No enquiries found.");
			return;
		}
		System.out.println("Here are your enquiries: ");
		EnquiriesViewer.printEnquiries(enquiries);
	}

	public static List<Enquiry> getAssignedProjectUnrepliedEnquiries(HDBOfficer officer, Project project)
	{
		List<Enquiry> unrepliedEnquiries = new ArrayList<>();
    	for (Enquiry enquiry : project.getEnquiries())
    	{
    		if (enquiry.getAnswer().equals("(No reply yet)")) unrepliedEnquiries.add(enquiry);
    	}
    	return unrepliedEnquiries;
	}

	public static void viewAssignedProjectUnrepliedEnquiries(HDBOfficer officer, Project project)
	{
		List<Enquiry> listUnrepliedEnquiries = getAssignedProjectUnrepliedEnquiries(officer, project);
		if (listUnrepliedEnquiries.isEmpty())
		{
			System.out.println("No unreplied enquiries found.");
			return;
		}
		System.out.println("Here are your unreplied enquiries:");
		EnquiriesViewer.printEnquiries(listUnrepliedEnquiries);
	}

	public static void replyEnquiry(HDBOfficer officer) {
		Project selectedProject = selectProject(officer.getAssignedProjects());
		if (selectedProject == null) return;

		List<Enquiry> unrepliedEnquiries = getAssignedProjectUnrepliedEnquiries(officer, selectedProject);
		viewAssignedProjectUnrepliedEnquiries(officer, selectedProject);
		if (unrepliedEnquiries.isEmpty()) return;

		int enquiry_choice;
		while(true)
		{
			System.out.print("Please enter the number of the enquiry that you want to reply: ");
			enquiry_choice = sc.nextInt();
			sc.nextLine();
			if(enquiry_choice>unrepliedEnquiries.size() || enquiry_choice<=0)
			{
				System.out.printf("Invalid enquiry. Please enter a number from 1-%d.\n",unrepliedEnquiries.size());
			}
			else break;
		}

		Enquiry enquiry = unrepliedEnquiries.get(enquiry_choice - 1);
		System.out.println("Enter your response:");
		String response = sc.nextLine();

		System.out.print("Confirm reply (Y/N): ");
		String confirm = sc.nextLine();
		if (confirm.equalsIgnoreCase("Y")) {
			enquiry.reply(response);
			EnquiryService.updateEnquiries();
			System.out.println("Enquiry replied successfully.");
		} else {
			System.out.println("Reply cancelled.");
		}
	}


	private static Project selectProject(List<Project> projects) {
		if (projects.isEmpty()) {
			System.out.println("No projects available.");
			return null;
		}

		System.out.println("Select a project:");
		for (int i = 0; i < projects.size(); i++) {
			System.out.printf("(%d) %s\n", i + 1, projects.get(i).getProjectName());
		}

		while (true) {
			int choice = InputHelper.readInt("Enter choice (0 to cancel): ");
			if (choice == 0) return null;
			if (choice >= 1 && choice <= projects.size()) {
				return projects.get(choice - 1);
			}
			System.out.println("Invalid choice. Please select a number from 1 to " + projects.size() + ", or 0 to cancel.");
		}
	}


}
