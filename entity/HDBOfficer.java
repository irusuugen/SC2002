package entity;

import java.util.*;

public class HDBOfficer extends User{
	private String officerID;
	private Project assignedProject;
	private List<Registration> registrationList = new ArrayList<>();
	public HDBOfficer(String name, String nric, String password, int age, boolean isMarried)
	{
		super(name, nric, password, age, isMarried);
	}
	
	// public void registerForProject(Project project)
	// {
	// 	Registration registration=new Registration(this,project);
	// 	project.registerOfficer(registration);
	// 	this.registrationList.add(registration);
	// }
	public void setAssignedProject(Project project)
	{
		this.assignedProject=project;
	}
	public Project getAssignedProject()
	{
		return this.assignedProject;
	}
	public List<Registration> getRegistrationList()
	{
		return this.registrationList;
	}
	// public String checkRegistrationStatus(Project project)
	// {
	// 	return this.registrationList.stream().filter(r -> r.getProject()==project).findFirst().get().getStatus();
	// }
	public ArrayList<Enquiry> viewEnquiries()
	{
		return assignedProject.getEnquiries();
	}
	public void replyEnquiry(String response, Enquiry enquiry)
	{
		enquiry.reply(response);
	}
	// public void updateApplication(Application application)
	// {
	// 	application.getProject().addOccupiedFlat(application.getFlatType,1);
	// 	application.markBooked();
	// }
}
