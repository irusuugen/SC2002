package control;

import java.util.*;
import entity.*;

public class HDBManagerRegistrationController {
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
