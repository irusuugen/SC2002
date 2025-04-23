package entity;

/**
 * Represents a registration of an officer for a specific project.
 * This class holds details about the officer's registration status, the assigned project, and the officer.
 */
public class Registration {
  	private Project registeredProject;
	private Status registrationStatus;
	private HDBOfficer registeredOfficer;

	/**
	 * Constructs a new registration with the specified officer and project, and sets the registration status to PENDING.
	 *
	 * @param officer HDB officer being registered
	 * @param project Project the officer is registered for
	 */
	public Registration(HDBOfficer officer,Project project)
	{
    this.registeredOfficer=officer;
		this.registeredProject=project;
		this.registrationStatus=Status.PENDING;
	}

	/**
	 * Constructs a new registration with the specified officer, project, and registration status.
	 *
	 * @param officer the HDB officer being registered
	 * @param project the project the officer is registered for
	 * @param status the status of the registration
	 */
	public Registration(HDBOfficer officer,Project project, Status status)
	{
    this.registeredOfficer=officer;
		this.registeredProject=project;
		this.registrationStatus=status;
	}

	/**
	 * Sets registration status
	 * @param status Status to change to
	 */
	public void setStatus(Status status)
	{
		this.registrationStatus=status;
	}

	/**
	 *
	 * @return Status of registration
	 */
	public Status getStatus()
	{
		return this.registrationStatus;
	}

	/**
	 *
	 * @return Project that the registration is associated with
	 */
	public Project getProject()
	{
		return this.registeredProject;
	}

	/**
	 *
	 * @return Officer who made the registration
	 */

	public HDBOfficer getRegisteredOfficer(){
		return this.registeredOfficer;
	}
}
