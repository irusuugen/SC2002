package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for filtering BTO projects
 * based on criteria such as flat availability and neighborhood.
 */

public class Filter {

    /**
     * Filters the given list of projects to only include those
     * with a minimum number of available flats of the specified type.
     *
     * @param projects The list of projects to filter.
     * @param type The type of flat (e.g., 2-room or 3-room).
     * @param minAvailable The minimum number of available flats required.
     * @return A list of projects that meet the availability criteria.
     */
    public static List<Project> filterByAvailableFlats(List<Project> projects, FlatType type, int minAvailable) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getNumFlatAvailable(type) >= minAvailable) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    /**
     * Filters the given list of projects to only include those
     * located in the specified neighborhood.
     *
     * @param projects The list of projects to filter.
     * @param neighborhood The neighborhood name to match (case-insensitive).
     * @return A list of projects located in the specified neighborhood.
     */
    public static List<Project> filterByNeighborhood(List<Project> projects, String neighborhood) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getNeighborhood().equalsIgnoreCase(neighborhood)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    /**
     * Filters the given list of projects based on a selected filter criteria.
     *
     * @param projects The list of projects to filter.
     * @param filterChoice The filter criteria to apply (1, 2, or 3).
     * @param minAvailable The minimum number of available flats (used for choices 1 and 2).
     * @param neighborhood The neighborhood to match (used for choice 3).
     * @param type The type of flat to check (used for choices 1 and 2).
     * @return A filtered list of projects based on the selected criteria.
     */
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