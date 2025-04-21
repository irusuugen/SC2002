/* Class is for storage and access to list of all projects in the database */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private static ProjectRepository projectRepository;
    private static List<Project> projectList = new ArrayList<>();

    public static boolean startProjectStorage(ProjectRepository repo) {
        projectRepository = repo;
        projectList.clear();
        projectList = projectRepository.loadAllProjects();
        return true;
    }

    public static List<Project> getAllProjects() {
        return projectList;
    }

    public static void updateProjects(){
        List<Project> projects = new ArrayList<>(projectList);
        projectRepository.saveAllProjects(projects);
    }
    public static void addProject(Project p) {
       projectList.add(p);
    }

    public static void removeProject(Project p) {
        projectList.remove(p);
    }
}