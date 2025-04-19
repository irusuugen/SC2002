package entity;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectFilter {
    private String locationFilter;
    private FlatType flatTypeFilter;
    private Boolean visibilityFilter;  // Changed to Boolean to support null
    private boolean sortByAlphabetical = true;

    // Setters
    public void setLocationFilter(String location) {
        this.locationFilter = location;
    }

    public void setFlatTypeFilter(String flatType) {
        this.flatTypeFilter = flatType == null ? null : 
            flatType.equalsIgnoreCase("2-Room") ? FlatType.TWOROOMS : FlatType.THREEROOMS;
    }

    public void setVisibilityFilter(Boolean visible) {  // Changed to accept Boolean
        this.visibilityFilter = visible;
    }

    public void setSortByAlphabetical(boolean sort) {
        this.sortByAlphabetical = sort;
    }

    // Getters
    public String getLocationFilter() {
        return locationFilter;
    }

    public FlatType getFlatTypeFilter() {
        return flatTypeFilter;
    }

    public Boolean getVisibilityFilter() {
        return visibilityFilter;
    }

    public boolean isSortByAlphabetical() {
        return sortByAlphabetical;
    }

    public void clearFilters() {
        this.locationFilter = null;
        this.flatTypeFilter = null;
        this.visibilityFilter = null;
        this.sortByAlphabetical = true;
    }

    public List<Project> applyFilters(List<Project> projects) {
        return projects.stream()
            .filter(p -> locationFilter == null || 
                p.getNeighborhood().equalsIgnoreCase(locationFilter))
            .filter(p -> flatTypeFilter == null || 
                p.getNumFlatAvailable(flatTypeFilter) > 0)
            .filter(p -> visibilityFilter == null || 
                p.isVisible() == visibilityFilter)
            .sorted((p1, p2) -> sortByAlphabetical ? 
                p1.getProjectName().compareTo(p2.getProjectName()) : 
                p2.getProjectName().compareTo(p1.getProjectName()))
            .collect(Collectors.toList());
    }
}