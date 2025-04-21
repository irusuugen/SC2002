package entity;

import java.util.*;

public class HDBOfficer extends Applicant {
	private Project assignedProject;
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
