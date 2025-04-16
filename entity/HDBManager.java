package entity;

import java.util.*;

public class HDBManager extends User {
    private List<Project> createdProjects;

    public HDBManager(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried);
        this.createdProjects = new ArrayList<>();
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void addCreatedProject(Project p) {
        createdProjects.add(p);
    }

    public void toggleVisibility(Project project) {
        if (createdProjects.contains(project)) {
            //project.setVisibility(!project.getVisibility());
        }
    }

    public void approveOfficer(Project project, HDBOfficer officer) {
        if (project.checkOfficerSlot()) {
            project.addOfficer(officer);
            project.unregisterOfficer(officer);
        } else {
            System.out.println("No more officer slots available for this project.");
        }
    }

    public void rejectOfficer(Project project, HDBOfficer officer) {
        project.unregisterOfficer(officer);
    }

    public void approveApplication(Application application) {
        Project project = application.getProject();
        FlatType type = FlatType.valueOf(application.getFlatType());
        if (project.getNumFlatAvailable(type) > 0) {
            application.markSuccessful();
            project.addOccupiedFlat(type, 1);
        } else {
            application.markUnsuccessful();
        }
    }

    public void rejectApplication(Application application) {
        application.markUnsuccessful();
    }

    public void approveWithdrawal(Application application) {
        application.withdraw();
        FlatType type = FlatType.valueOf(application.getFlatType());
        application.getProject().removeOccupiedFlat(type, 1);
    }

    public void viewAllEnquiries(List<Project> allProjects) {
        for (Project project : allProjects) {
            System.out.println("\nProject: " + project.getProjectName());
            for (Enquiry e : project.getEnquiries()) {
                System.out.println(e);
            }
        }
    }

    public void replyToEnquiry(Enquiry enquiry, String response) {
        enquiry.reply(response);
    }

    public List<Application> generateReport(List<Application> allApplications, String filter) {
        List<Application> result = new ArrayList<>();
        for (Application app : allApplications) {
            if (filter.equalsIgnoreCase("married") && app.getApplicant().isMarried()) {
                result.add(app);
            } else if (filter.equalsIgnoreCase("single") && !app.getApplicant().isMarried()) {
                result.add(app);
            } else if (filter.equalsIgnoreCase("all")) {
                result.add(app);
            }
        }
        return result;
    }
}
