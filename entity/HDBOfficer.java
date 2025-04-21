package entity;

import java.util.*;

public class HDBOfficer extends Applicant {
	private Project assignedProject;
	private List<Registration> registrationList = new ArrayList<>();

	public HDBOfficer(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried, Role.HDB_OFFICER);
    }
	
	public void setAssignedProject(Project project) {
		assignedProject = project;
	}

	public void addRegistration(Registration registration) {
		registrationList.add(registration);
	}

	public Project getAssignedProject()	{
		return this.assignedProject;
	}
	public List<Registration> getRegistrationList() {
		return this.registrationList;
	}
	
}
