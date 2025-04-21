package control;

import boundary.ProjectViewer;
import entity.*;
import repository.ProjectService;

import java.util.ArrayList;
import java.util.List;

public class ApplicantProjectController {
    public static List<Project> getOpenProjects(Applicant applicant) {
        List<Project> openProjects = new ArrayList<>();
        for (Project project : ProjectService.getAllProjects()) {
            if (!project.isVisible() || !project.checkOpeningPeriod()) continue;
            if (applicant instanceof HDBOfficer officer) {
                if (project.getOfficerSlotList().contains(officer)) continue;
            }
            if (applicant.getUserGroup() == UserGroup.MARRIED &&
                    (project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 || project.getNumFlatAvailable(FlatType.THREEROOMS) > 0)) {
                openProjects.add(project);
            } else if (applicant.getUserGroup() == UserGroup.SINGLE &&
                    project.getNumFlatAvailable(FlatType.TWOROOMS) > 0) {
                openProjects.add(project);
            }
        }
        return openProjects;
    }

    public static void viewOpenProjects(Applicant applicant) {
        List<Project> openProjects = getOpenProjects(applicant);
        ProjectViewer.printProjects(openProjects, applicant);
    }
}
