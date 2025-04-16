package control;

import java.util.*;
import entity.*;

public class HDBManagerProjectController {
    public void addCreatedProject(HDBManager manager, Project p) {
        List<Project> createdProjects = manager.getCreatedProjects();
        createdProjects.add(p);
    }

    public void toggleVisibility(HDBManager manager, Project project) {
        if (manager.getCreatedProjects().contains(project)) {
            project.setVisibility(!project.getVisibility());
        }
    }
}
