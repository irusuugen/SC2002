/**
 * Represents a dynamic filter and sorting configuration for BTO project listings.
 *
 * <p>This class allows filtering projects by location and available flat type,
 * as well as sorting the results alphabetically (A-Z or Z-A).</p>
 */

package entity;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectFilter {
    private String locationFilter;
    private FlatType flatTypeFilter;
    private Boolean sortByAlphabetical = null; // true = A-Z, false = Z-A, null = no sort

    // Setters
    /**
     * Sets the location (neighborhood) filter.
     *
     * @param location The neighborhood name to filter by.
     */
    public void setLocationFilter(String location) {
        this.locationFilter = location;
    }

    /**
     * Sets the flat type filter using a string value.
     *
     * @param flatType The flat type string (e.g., "2-Room", "3-Room").
     *                 If the string is null or invalid, the filter is cleared.
     */
    public void setFlatTypeFilter(String flatType) {
        this.flatTypeFilter = flatType == null ? null :
                flatType.equalsIgnoreCase("2-Room") ? FlatType.TWOROOMS :
                        flatType.equalsIgnoreCase("3-Room") ? FlatType.THREEROOMS : null;
    }

    /**
     * Sets whether to sort projects alphabetically by name.
     *
     * @param sort True for A-Z, false for Z-A, or null for no sorting.
     */
    public void setSortByAlphabetical(Boolean sort) {
        this.sortByAlphabetical = sort;
    }

    // Getters

    /**
     * Gets the current location filter.
     *
     * @return The neighborhood name filter, or null if none is set.
     */
    public String getLocationFilter() {
        return locationFilter;
    }

    /**
     * Gets the current flat type filter.
     *
     * @return The flat type filter, or null if none is set.
     */
    public FlatType getFlatTypeFilter() {
        return flatTypeFilter;
    }

    /**
     * Gets the current sorting configuration.
     *
     * @return True for A-Z sorting, false for Z-A, or null for no sorting.
     */
    public Boolean getSortByAlphabetical() {
        return sortByAlphabetical;
    }

    /**
     * Clears all filters and sorting preferences.
     */
    public void clearFilters() {
        this.locationFilter = null;
        this.flatTypeFilter = null;
        this.sortByAlphabetical = null; // no sorting by default
    }

    /**
     * Applies the currently configured filters and sorting to a list of projects.
     *
     * @param projects The list of projects to be filtered and/or sorted.
     * @return A new list of projects that match the filter criteria and sorting order.
     */
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
