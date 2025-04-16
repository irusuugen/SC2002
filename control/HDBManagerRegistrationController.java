package control;

import entity.*;

public class HDBManagerRegistrationController {
    public void approveOfficer(Project project, Registration registration) {
        if (project.checkOfficerSlot()) {
            project.addOfficer(registration.getRegisteredOfficer());
            project.unregisterOfficer(registration);
        } else {
            System.out.println("No more officer slots available for this project.");
        }
    }

    public void rejectOfficer(Project project, Registration registration) {
        project.unregisterOfficer(registration);
    }

    public void approveApplication(Application application) {
        Project project = application.getProject();
        FlatType type = application.getFlatType();
        if (project.getNumFlatAvailable(type) > 0) {
            application.markSuccessful();
            project.addOccupiedFlat(type);
        } else {
            application.markUnsuccessful();
        }
    }

    public void rejectApplication(Application application) {
        application.markUnsuccessful();
    }

    public void approveWithdrawal(Application application) {
        application.withdraw();
        FlatType type = application.getFlatType();
        application.getProject().removeOccupiedFlat(type);
    }
}
