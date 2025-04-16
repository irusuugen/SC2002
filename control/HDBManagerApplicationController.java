package control;

import entity.*;

public class HDBManagerApplicationController {
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
