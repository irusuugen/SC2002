/**
 * Represents an HDB Officer in the BTO Management System.
 */

package entity;

import java.util.*;

public class HDBOfficer extends Applicant {
	private Project assignedProject;
	private List<Registration> registrationList = new ArrayList<>();

	/**
	 * Constructs a new HDBOfficer with the specified details.
	 *
	 * @param name      Name of the HDB Officer
	 * @param nric      NRIC of the HDB Officer
	 * @param password  Password of the HDB Officer
	 * @param age       Age of the HDB Officer
	 * @param isMarried Marital status of the HDB Officer
	 */
	public HDBOfficer(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried, Role.HDB_OFFICER);
    }

	/**
	 * Checks if the HDB Officer is eligible to register for the given project.
	 * An officer can only register for a project if they are not already assigned to it and have not
	 * already registered for it.
	 *
	 * @param project Project to check eligibility for
	 * @return {@code true} if the officer is eligible to register for the project, otherwise {@code false}
	 */

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
