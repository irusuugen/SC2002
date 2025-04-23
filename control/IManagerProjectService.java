package control;

import entity.HDBManager;
import entity.Project;
import entity.UserSession;

import java.util.List;

public interface IManagerProjectService {
    void createProject(HDBManager manager);
    void deleteProject(HDBManager manager, List<Project> allProjects);
    void editProject(HDBManager manager);
    Project selectProject(List<Project> projects);
    void viewAllProjects(HDBManager manager, List<Project> allProjects, UserSession session);
    void toggleProjectVisibility(HDBManager manager);
}
