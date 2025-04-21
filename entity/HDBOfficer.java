package entity;

import java.util.*;

public class HDBOfficer extends Applicant {
	private List<Project> assignedProjects = new ArrayList<>();
	private List<Registration> registrationList = new ArrayList<>();

	public HDBOfficer(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried, Role.HDB_OFFICER);
    }

	@Override
	public boolean isEligibleForProject(Project project) {
		// Check if officer is already assigned to this project
		if (project.getOfficerSlotList().contains(this)) {
			return false;
		}

		// Check if officer has already registered for this project
		for (Registration reg : registrationList) {
			if (reg.getProject().equals(project)) {
				return false;
			}
		}

		return true;
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
