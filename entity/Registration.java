package entity;

public class Registration {
  private Project registeredProject;
	private String registrationStatus;
	private HDBOfficer registeredOfficer;
	public Registration(HBBOfficer officer,Project project)
	{
    this.registeredOfficer=officer;
		this.registeredProject=project;
		this.registrationStatus="Pending";
	}
	public void setStatus(String status)
	{
		this.registrationStatus=status;
	}
	public String getStatus()
	{
		return this.registrationStatus;
	}
	public Project getProject()
	{
		return this.registeredProject;
	}
}
