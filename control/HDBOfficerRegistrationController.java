package control;

import java.util.*;
import boundary.ProjectViewer;
import entity.*;
import repository.*;
import utils.*;


public class HDBOfficerRegistrationController{
	
	private static Scanner sc = new Scanner(System.in);
	
	public static Status checkRegistrationStatus(HDBOfficer officer, Project project)
	{
		return officer.getRegistrationList().stream().filter(r -> r.getProject() == project).findFirst().get().getStatus();
	}
	public static List<Project> getRegistrableProjects(HDBOfficer officer)
	{
		List<Project> registrableProjects = new ArrayList<>();
    		for (Project project : ProjectService.getAllProjects())
    		{
            		if (project.getVisibility() && project!=officer.getAssignedProject() && (officer.getApplication()==null || project!=officer.getApplication().getProject()))
            		{
            			registrableProjects.add(project);
            		}
        	}
    		return registrableProjects;
	}
	public static void viewRegistrableProjects(HDBOfficer officer)
	{
		List<Project> registrableProjects = getRegistrableProjects(officer);
    		ProjectViewer.printProjects(registrableProjects);
	}
	public static void registerForProject(HDBOfficer officer)
	{
		if(officer.getAssignedProject()!=null)
    		{
    			System.out.println("You are a HDB Officer for another project.");
    			return;
    		}
		viewRegistrableProjects(officer);
    		while(true)
    		{
        		System.out.print("Please enter the project name that you want to register for: ");
            		String projectName = sc.nextLine();
            		Project project = getRegistrableProjects(officer).stream().filter(p -> projectName.equals(p.getProjectName())).findFirst().orElse(null);
            		if(project==null) System.out.println("Cannot find this project");
            		else
            		{
            			Registration registration=new Registration(officer, project);
        			project.registerOfficer(registration);
        			List<Registration> registrationList = officer.getRegistrationList();
        			registrationList.add(registration);
                		System.out.println("Registered");
                		break;
            		}
    		}
	}
	public static void viewRegistration(HDBOfficer officer)
	{
		List<Registration> registrationList = officer.getRegistrationList();
    		if(registrationList.isEmpty())
    		{
    			System.out.println("No registration");
    			break;
    		}
    		for(Registration registration : registrationList)
    		{
    			System.out.println("Project Name: %s, Status: %s",registration.getProject().getProjectName(),registration.getStatus());
    		}
	}
}
