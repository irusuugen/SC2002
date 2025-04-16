package control;

import boundary.ProjectViewer;
import entity.*;
import java.util.ArrayList;
import java.util.List;
import repository.*;

public class ApplicantController {

    public static List<Project> getOpenProjects(Applicant applicant) {
        List<Project> openProjects = new ArrayList<>();
        if (applicant.isMarried() && applicant.getAge() >= 21) {
            for (Project project : ProjectService.getAllProjects()) {
                if (project.getVisibility()) {
                    openProjects.add(project);
                }
            }
        } else if (!applicant.isMarried() && applicant.getAge() >= 35) {
            for (Project project : ProjectService.getAllProjects()) {
                if (project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 && project.getVisibility()) {
                    openProjects.add(project);
                }
            }
        }
        System.out.println("Loaded projects: " + openProjects.size());
        return openProjects;
    }

    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects);
    }

    // public boolean applyForProject(Project project, String flatType) {
    //     if (this.application != null) {
    //         return false; // Prevent applicants from applying for more than one project
    //     }
    //     this.application = new Application(project, flatType, this);
    //     return true;
    // }

    // public void viewApplication() {
    //     if (application != null) {
    //         application.getProject().printProjectDetails();
    //         System.out.println(application); // Show application info using toString()
    //     } else {
    //         System.out.println("No application submitted.");
    //     }
    // }

    // public boolean withdrawApplication() {
    //     if (this.application == null) return false; // Checks for existing application
    //     application.withdraw();
    //     this.application = null;
    //     return true;
    // }

    // public void submitEnquiry(String message, Project project) {
    //     enquiries.add(new Enquiry(message, project));
    // }

    // public void editEnquiry(Enquiry enquiry, String newMessage) {
    //     if (enquiries.contains(enquiry)) {
    //         enquiry.edit(newMessage);
    //     }
    // }

    // public void deleteEnquiry(Enquiry enquiry) {
    //     enquiries.remove(enquiry);
    // }

    // public void viewEnquiries() {
    //     for (Enquiry enquiry : enquiries) {
    //         System.out.println(enquiry);
    //     }
    // }

}