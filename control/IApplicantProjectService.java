package control;

import entity.Applicant;
import entity.Project;
import java.util.List;

public interface IApplicantProjectService {
    List<Project> getOpenProjects(Applicant applicant);
    void viewOpenProjects(Applicant applicant);
    Project findProjectByName(Applicant applicant, String projectName);
}
