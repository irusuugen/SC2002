package entity;

public class Registration {
  	private Project registeredProject;
	private Status registrationStatus;
	private HDBOfficer registeredOfficer;
	public Registration(HDBOfficer officer,Project project)
	{
    this.registeredOfficer=officer;
		this.registeredProject=project;
		this.registrationStatus=Status.PENDING;
	}
	public void setStatus(Status status)
	{
		this.registrationStatus=status;
	}
	public Status getStatus()
	{
		return this.registrationStatus;
	}
	public Project getProject()
	{
		return this.registeredProject;
	}
}
