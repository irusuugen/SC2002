package control;

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
		while(true){
			System.out.print("Please enter applicant's NRIC: ");
			String nric = sc.nextLine();
			Application application = fetchApplicationFromNRIC(nric);
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
		// generate receipt
	}
	
}
