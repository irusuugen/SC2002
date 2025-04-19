package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import entity.*;
import repository.*;
import utils.*;

public class HDBOfficerApplicationController{

	private static Scanner sc = new Scanner(System.in);
	
	public static void updateApplication(HDBOfficer officer)
	{
		Application application;
		while(true){
			System.out.print("Please enter applicant's NRIC: ");
			String nric = sc.nextLine();
			application = ApplicationService.fetchApplicationFromNRIC(nric);
			if(application == null){
				System.out.print("Application was not found. Retry? (Y/N): ");
                String retry = sc.nextLine();
                if (retry.equalsIgnoreCase("N")) return;
			}
			else if (!officer.getAssignedProjects().contains(application.getProject())){
				System.out.print("Officer is not responsible for the applicant's project. Retry? (Y/N): ");
            	String retry = sc.nextLine();
                if (retry.equalsIgnoreCase("N")) return;
			}
			else if(!application.isBookingRequested()){
				System.out.print("Booking request has not been submitted. Retry? (Y/N): ");
            	String retry = sc.nextLine();
                if (retry.equalsIgnoreCase("N")) return;
			}
			else {
				System.out.println("Application found. Confirm successful booking? (Y/N): ");
				String confirm = sc.nextLine();
				if (confirm.equalsIgnoreCase("Y")) {
					application.getProject().addOccupiedFlat(application.getFlatType());
					application.markBooked();
					break;
				} else {
					System.out.println("Application was not booked. Retry? (Y/N): ");
					String retry = sc.nextLine();
					if (retry.equalsIgnoreCase("N")) return;
				}
			}
		}
		ClearPage.clearPage();
		printBookingReceipt(application, officer);
	}

	public static void printBookingReceipt(Application application, HDBOfficer officer) {
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
