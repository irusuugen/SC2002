package entity;

import java.util.*;

public class HDBOfficer extends Applicant{
	private Project assignedProject;
	private List<Registration> registrationList = new ArrayList<>();
	public HDBOfficer(String name, String nric, String password, int age, boolean isMarried)
	{
		super(name, nric, password, age, isMarried);
	}
	
	public void setAssignedProject(Project project)
	{
		this.assignedProject = project;
	}
	public Project getAssignedProject()	{
		return this.assignedProject;
	}
	public List<Registration> getRegistrationList()
	{
		return this.registrationList;
	}
	
}
