package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import entity.*;
import repository.*;
import utils.*;

public class HDBOfficerApplicationController{

	private static Scanner sc = new Scanner(System.in);

	public static Application fetchApplicationFromNRIC(String NRIC){
		List<User> users = UserService.getAllUsers();
		User user = users.stream().filter(u -> u.getNric().equals(NRIC)).findFirst().orElse(null);
		if(user != null && (user instanceof Applicant)) return ((Applicant) user).getApplication(); 
		return null;
	}
	
	public static void updateApplication(HDBOfficer officer)
	{
		Application application;
		while(true){
			System.out.print("Please enter applicant's NRIC: ");
			String nric = sc.nextLine();
			application = fetchApplicationFromNRIC(nric);
			if(application == null){
				System.out.print("Application was not found. Retry? (Y/N): ");
                String retry = sc.nextLine();
                if (retry.equalsIgnoreCase("N")) return;
			}
			else if(application.getProject() != officer.getAssignedProject()){
				System.out.print("Officer is not responsible for the project. Retry? (Y/N): ");
            	String retry = sc.nextLine();
                if (retry.equalsIgnoreCase("N")) return;
			}
			else{
				application.getProject().addOccupiedFlat(application.getFlatType());
				application.markBooked();
				break;
			}
		}
		printBookingReceipt(application, officer);
	}
	
	public static void printBookingReceipt(Application application, HDBOfficer officer){
		System.out.printf("=== HDB FLAT BOOKING RECEIPT ===\n" + 
						"Receipt No: %s\n" + 
						"Booking Date: %s\n\n","REC-" + System.currentTimeMillis(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));  
		System.out.printf("=== APPLICANT DETAILS ===\n" + 
						"Name: %s\n" + 
						"NRIC: %s\n" + 
						"Age: %d\n\n", application.getApplicant().getName(), application.getApplicant().getNric(), application.getApplicant().getAge()); 
		System.out.printf("=== FLAT DETAILS ===\n" +
						"Project: %s\n" +
						"Flat Type: %s\n" +  
						"Price: $%d\n" +
						"==========================\n\n", application.getProject().getProjectName(), application.getFlatType(), application.getProject().getSellingPrice(application.getFlatType()));
	}
}
