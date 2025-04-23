/**
 * Handles the enquiry-related operations for an HDB Officer,
 * including viewing and replying to enquiries.
 */

package control;

import java.util.*;
import boundary.EnquiriesViewer;
import entity.*;
import repository.EnquiryService;
import utils.*;

public class HDBOfficerEnquiryController implements IOfficerEnquiryService {
	
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Displays all enquiries related to the project assigned to the specified officer.
	 *
	 * @param officer the HDB officer whose assigned project's enquiries are to be viewed
	 */
	public void viewEnquiries(HDBOfficer officer)
	{
		Project project = officer.getAssignedProject();
		if (project == null) {
			return;
		}
		List<Enquiry> enquiries = project.getEnquiries();
		if (enquiries.isEmpty())
		{
			System.out.println("No enquiries found.");
			return;
		}
		System.out.println("Here are your enquiries: ");
		EnquiriesViewer.printEnquiries(enquiries);
	}

	/**
	 * Retrieves a list of enquiries that have not yet been replied to for the assigned project
	 *
	 * @param project The project whose unreplied enquiries are to be fetched
	 * @return List of unreplied enquiries
	 */
	public List<Enquiry> getUnrepliedEnquiries(Project project)
	{
		List<Enquiry> unrepliedEnquiries = new ArrayList<>();
    	for (Enquiry enquiry : project.getEnquiries())
    	{
    		if (enquiry.getAnswer().equals("(No reply yet)")) unrepliedEnquiries.add(enquiry);
    	}
    	return unrepliedEnquiries;
	}

	/**
	 * Displays all unreplied enquiries for a given project.
	 *
	 * @param project Project whose unreplied enquiries are to be viewed
	 */
	public void viewUnrepliedEnquiries(Project project)
	{
		List<Enquiry> listUnrepliedEnquiries = getUnrepliedEnquiries(project);
		if (listUnrepliedEnquiries.isEmpty())
		{
			System.out.println("No unreplied enquiries found.");
			return;
		}
		System.out.println("Here are your unreplied enquiries:");
		EnquiriesViewer.printEnquiries(listUnrepliedEnquiries);
	}

	/**
	 * Allows the HDB officer to reply to one of the unreplied enquiries for their assigned project.
	 *
	 * @param enquiry The enquiry that the officer is replying to
	 */
	public void replyEnquiry(Enquiry enquiry) {
		if (enquiry==null) {
			return;
		}
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

	public Enquiry selectAssignedProjectEnquiry(HDBOfficer officer) {
		Project project = officer.getAssignedProject();
		if (project == null)  {
			System.out.println("No project found.");
			return null;
		}

		List<Enquiry> unrepliedEnquiries = getUnrepliedEnquiries(project);
		viewUnrepliedEnquiries(project);
		if (unrepliedEnquiries.isEmpty()) {
			System.out.println("No enquiries found.");
			return null;
		}

		int enquiry_choice;
		while (true) {
			enquiry_choice = InputHelper.readInt("Please enter the number of the enquiry that you want to reply: ");
			if (enquiry_choice > 0 && enquiry_choice <= unrepliedEnquiries.size()) {
				break;
			} else {
				System.out.printf("Invalid enquiry. Please enter a number from 1-%d.\n", unrepliedEnquiries.size());
			}
		}

		Enquiry enquiry = unrepliedEnquiries.get(enquiry_choice - 1);
		return enquiry;
	}

}
