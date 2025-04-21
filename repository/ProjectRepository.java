package repository;

import entity.Project;
import java.util.List;

public interface ProjectRepository {
    List<Project> loadAllProjects();
    void saveAllProjects(List<Project> projects);
}