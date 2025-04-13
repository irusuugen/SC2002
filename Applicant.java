import java.util.List;
import java.util.ArrayList;

public class Applicant extends User {
    private Application application;
    private List<Enquiry> enquiries;

    public Applicant(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried);
        this.enquiries = new ArrayList<>();
        this.application = null;
    }

    public void viewOpenProjects(List<Project> projectList) {
        if (isMarried() && getAge() >= 21) {
            for (Project project : projectList) {
                if (project.getVisibility()) {
                    project.printProjectDetails();
                }
            }
        } else if (!isMarried() && getAge() >= 35) {
            for (Project project : projectList) {
                if (project.getNumFlatAvailable(flatType.TWOROOMS) > 0 && project.getVisibility()) {
                    project.printProjectDetails();
                }
            }
        }
    }

    public boolean applyForProject(Project project, String flatType) {
        if (this.application != null) {
            return false; // Prevent applicants from applying for more than one project
        }
        this.application = new Application(project, flatType, this);
        return true;
    }

    public void viewApplication() {
        if (application != null) {
            application.getProject().printProjectDetails();
            System.out.println(application); // Show application info using toString()
        } else {
            System.out.println("No application submitted.");
        }
    }

    public boolean withdrawApplication() {
        if (this.application == null) return false; // Checks for existing application
        application.withdraw();
        this.application = null;
        return true;
    }

    public void submitEnquiry(String message, Project project) {
        enquiries.add(new Enquiry(message, project));
    }

    public void editEnquiry(Enquiry enquiry, String newMessage) {
        if (enquiries.contains(enquiry)) {
            enquiry.edit(newMessage);
        }
    }

    public void deleteEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    public void viewEnquiries() {
        for (Enquiry enquiry : enquiries) {
            System.out.println(enquiry);
        }
    }

    public Application getApplication() {
        return application;
    }
}
