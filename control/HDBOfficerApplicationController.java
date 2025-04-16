package control;

import java.util.*;
import entity.*;
import utils.*;

public class HDBOfficerApplicationController{

	private static Scanner sc = new Scanner(System.in);
	
	public static void updateApplication()
	{
		System.out.print("Please enter applicant’s NRIC: ");
        	String nric = sc.nextLine();
        	// search for application by nric
		application.getProject().addOccupiedFlat(application.getFlatType());
		application.markBooked();
		// generate receipt
	}
	
}
