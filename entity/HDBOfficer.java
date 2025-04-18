package entity;

import java.util.*;

public class HDBOfficer extends Applicant {
	private String officerID;
	private List<Project> assignedProjects = new ArrayList<>();
	private List<Registration> registrationList = new ArrayList<>();

	public HDBOfficer(String name, String nric, String password, int age, boolean isMarried)
	{
		super(name, nric, password, age, isMarried);
	}
	
	public void addAssignedProject(Project project) {
		assignedProjects.add(project);
	}

	public void addRegistration(Registration registration) {
		registrationList.add(registration);
	}

	public List<Project> getAssignedProjects()	{
		return this.assignedProjects;
	}
	public List<Registration> getRegistrationList() {
		return this.registrationList;
	}
	
}
