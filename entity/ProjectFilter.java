package entity;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectFilter {
    private String locationFilter;
    private FlatType flatTypeFilter;
    private Boolean sortByAlphabetical = null; // true = A-Z, false = Z-A, null = no sort

    // Setters
    public void setLocationFilter(String location) {
        this.locationFilter = location;
    }

    public void setFlatTypeFilter(String flatType) {
        this.flatTypeFilter = flatType == null ? null :
                flatType.equalsIgnoreCase("2-Room") ? FlatType.TWOROOMS :
                        flatType.equalsIgnoreCase("3-Room") ? FlatType.THREEROOMS : null;
    }

    public void setSortByAlphabetical(Boolean sort) {
        this.sortByAlphabetical = sort;
    }

    // Getters
    public String getLocationFilter() {
        return locationFilter;
    }

    public FlatType getFlatTypeFilter() {
        return flatTypeFilter;
    }

    public Boolean getSortByAlphabetical() {
        return sortByAlphabetical;
    }

    public void clearFilters() {
        this.locationFilter = null;
        this.flatTypeFilter = null;
        this.sortByAlphabetical = null; // no sorting by default
    }

    public List<Project> applyFilters(List<Project> projects) {
        return projects.stream()
                .filter(p -> locationFilter == null || p.getNeighborhood().equalsIgnoreCase(locationFilter))
                .filter(p -> flatTypeFilter == null || p.getNumFlatAvailable(flatTypeFilter) > 0)
                .sorted((p1, p2) -> {
                    if (sortByAlphabetical == null) return 0; // no sorting
                    return sortByAlphabetical
                            ? p1.getProjectName().compareToIgnoreCase(p2.getProjectName())
                            : p2.getProjectName().compareToIgnoreCase(p1.getProjectName());
                })
                .collect(Collectors.toList());
    }
}
