/**
 * Controller class that handles application-related actions performed by an HDB Officer.
 * This includes updating an application to confirm a successful flat booking and printing a booking receipt.
 */

package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;
import entity.*;
import repository.*;
import utils.*;

public class HDBOfficerApplicationController{

	private static Scanner sc = new Scanner(System.in);

	/**
	 * Allows the HDB Officer to update an application by marking it as successfully booked
	 *
	 * Prompts the officer to enter an applicant's NRIC, verifies that the applicant exists
	 * and is assigned to the officer's project, and confirms that a booking has been requested.
	 * If all validations pass and the officer confirms, the method updates the application and project data
	 * accordingly and prints a booking receipt.
	 *
	 * @param officer The officer performing the booking
	 */
	public static void updateApplication(HDBOfficer officer) {
		Application application;
		while (true) {
			System.out.print("Please enter applicant's NRIC: ");
			String nric = sc.nextLine();

			application = Stream.concat(
							UserService.getOfficers().stream(),
							UserService.getApplicants().stream()
					)
					.filter(user -> user.getNric().equals(nric))
					.map(Applicant::getApplication)
					.findFirst()
					.orElse(null);

			if (application == null) {
				if (!InputHelper.confirm("Application was not found. Retry?")) return;
			} else if (officer.getAssignedProject() == null || !officer.getAssignedProject().equals(application.getProject())) {
				if (!InputHelper.confirm("Officer is not responsible for the applicant's project. Retry?")) return;
			} else if (!application.isBookingRequested()) {
				if (!InputHelper.confirm("Booking request has not been submitted. Retry?")) return;
			} else {
				if (InputHelper.confirm("Application found. Confirm successful booking?")) {
					application.getProject().addOccupiedFlat(application.getFlatType());
					application.markBooked();
					ProjectService.updateProjects();
					ApplicationService.updateApplications();
					break;
				} else {
					if (!InputHelper.confirm("Application was not booked. Retry?")) return;
				}
			}
		}
		ClearPage.clearPage();
		printBookingReceipt(application);
	}

	/**
	 * Prints a formatted booking receipt for a successfully booked application.
	 *
	 * Displays receipt number, booking date, applicant details, and flat details
	 * in a box format
	 *
	 * @param application Application that has been booked.
	 */
	public static void printBookingReceipt(Application application) {
		BoxPrinter.printTopBorder();
		System.out.println(BoxPrinter.centerInBox("HDB FLAT BOOKING RECEIPT"));

		// Receipt Details
		BoxPrinter.printDivider();
		System.out.println(BoxPrinter.centerInBox("RECEIPT DETAILS"));
		BoxPrinter.printDivider();
		BoxPrinter.printRow("Receipt No", "REC-" + System.currentTimeMillis());
		BoxPrinter.printRow("Booking Date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		// Applicant Details
		BoxPrinter.printDivider();
		System.out.println(BoxPrinter.centerInBox("APPLICANT DETAILS"));
		BoxPrinter.printDivider();
		BoxPrinter.printRow("Name", application.getApplicant().getName());
		BoxPrinter.printRow("NRIC", application.getApplicant().getNric());
		BoxPrinter.printRow("Age", String.valueOf(application.getApplicant().getAge()));
		BoxPrinter.printRow("Marital Status", application.getApplicant().getUserGroup().toString());

		// Flat Details
		BoxPrinter.printDivider();
		System.out.println(BoxPrinter.centerInBox("FLAT DETAILS"));
		BoxPrinter.printDivider();
		BoxPrinter.printRow("Project", application.getProject().getProjectName());
		BoxPrinter.printRow("Flat Type", application.getFlatType().toString());
		BoxPrinter.printRow("Price", "$" + application.getProject().getSellingPrice(application.getFlatType()));

		BoxPrinter.printBottomBorder();
	}

}
