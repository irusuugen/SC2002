package entity;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    
    // Filter projects by available flats (2-room or 3-room)
    public static List<Project> filterByAvailableFlats(List<Project> projects, FlatType type, int minAvailable) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getNumFlatAvailable(type) >= minAvailable) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    // Filter projects by neighborhood
    public static List<Project> filterByNeighborhood(List<Project> projects, String neighborhood) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getNeighborhood().equalsIgnoreCase(neighborhood)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    // Get all the available projects based on the filter criteria
    public static List<Project> filterProjects(List<Project> projects, int filterChoice, int minAvailable, String neighborhood, FlatType type) {
        switch (filterChoice) {
            case 1:  
                return filterByAvailableFlats(projects, FlatType.TWOROOMS, minAvailable);  
            case 2:  
                return filterByAvailableFlats(projects, FlatType.THREEROOMS, minAvailable);  
            case 3:  
                return filterByNeighborhood(projects, neighborhood);  
            default:
                System.out.println("Invalid filter choice! Returning empty list.");
                return new ArrayList<>();  
        }
    }
}