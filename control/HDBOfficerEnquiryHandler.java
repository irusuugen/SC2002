package control;

import java.util.*;
import entity.*;
import utils.*;

public class HDBOfficerEnquiryHandler{
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void viewHandlingProjectEnquiries(HDBOfficer officer)
	{
		List<Enquiry> listEnquiries = officer.getAssignedProject().getEnquiries();
    		if(listEnquiries.isEmpty())
    		{
    			System.out.println("No enquiry");
    			return;
    		}
    		for (Enquiry enquiry : listEnquiries)
    		{
    			System.out.printf("Message: %s ,Reply: ",enquiry.getMessage());
    			if (enquiry.getAnswer()!=null) 
            		{
                		System.out.println(enquiry.getAnswer());
            		}
    			else 
            		{
                		System.out.println("(No reply yet)");
            		}
    		}
	}
	public static List<Enquiry> getHandlingProjectUnrepliedEnquiries(HDBOfficer officer)
	{
		List<Enquiry> listUnrepliedEnquiries = new ArrayList<>();
    	for (Enquiry enquiry : officer.getAssignedProject().getEnquiries())
    	{
    		if (enquiry.getAnswer()==null) listUnrepliedEnquiries.add(enquiry);
    	}
    	return listUnrepliedEnquiries;
	}
	public static void viewHandlingProjectUnrepliedEnquiries(HDBOfficer officer)
	{
		List<Enquiry> listUnrepliedEnquiries = getHandlingProjectUnrepliedEnquiries(officer);
    		if(listUnrepliedEnquiries.isEmpty())
    		{
    			System.out.println("No unreplied enquiry");
    			return;
    		}
    		System.out.println("Unreplied enquiries: ");
    		for (int i=0;i<listUnrepliedEnquiries.size();i++)
    		{
    			System.out.printf("(%d) Message: %s\n",i+1,listUnrepliedEnquiries.get(i).getMessage());
    		}
	}
	public static void replyEnquiry(HDBOfficer officer)
	{
		viewHandlingProjectUnrepliedEnquiries(officer);
		if(getHandlingProjectUnrepliedEnquiries(officer).isEmpty()) return;
		int enquiry_choice = sc.nextInt();
        	while(true)
    		{
        		System.out.print("Please enter the number of the enquiry that you want to reply: ");
        		enquiry_choice = sc.nextInt();
        		sc.nextLine();
        		if(enquiry_choice>getHandlingProjectUnrepliedEnquiries(officer).size() || enquiry_choice<=0)
        		{
        			System.out.printf("Invalid enquiry. Please enter a number from 1-%d.\n",getHandlingProjectUnrepliedEnquiries(officer).size());
        		}
        		else break;
    		}
    		System.out.print("Enter your response: ");
        	String response = sc.nextLine();
       	 	getHandlingProjectUnrepliedEnquiries(officer).get(enquiry_choice-1).reply(response);
        	System.out.println("Replied");
	}
}
