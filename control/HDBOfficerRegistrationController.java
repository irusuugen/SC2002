package control;

import java.util.*;

import entity.HDBOfficer;
import entity.Project;
import entity.Registration;
import entity.Status;

public class HDBOfficerRegistrationController{
	public void registerForProject(HDBOfficer officer, Project project)
	{
		Registration registration=new Registration(officer, project);
		project.registerOfficer(registration);
		List<Registration> registrationList = officer.getRegistrationList();
		registrationList.add(registration);
		officer.setRegistrationList(registrationList);
	}
	public Status checkRegistrationStatus(HDBOfficer officer, Project project)
	{
		return officer.getRegistrationList().stream().filter(r -> r.getProject() == project).findFirst().get().getStatus();
	}
}
