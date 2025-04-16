package control;

import java.util.*;

import entity.Enquiry;
import entity.HDBOfficer;

public class HDBOfficerEnquiryController{
	
	public ArrayList<Enquiry> viewEnquiries(HDBOfficer officer)
	{
		return officer.getAssignedProject().getEnquiries();
	}
	public void replyEnquiry(String response, Enquiry enquiry)
	{
		enquiry.reply(response);
	}
	
}
