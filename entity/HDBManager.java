/**
 * Represents an HDB Manager in the BTO Management System
 */


package entity;

import java.util.*;

public class HDBManager extends User {
    private List<Project> createdProjects;

    /**
     * Constructs a new HDBManager with the specified details.
     *
     * @param name      Name of the HDB Manager
     * @param nric      NRIC of the HDB Manager
     * @param password  Password of the HDB Manager
     * @param age       Age of the HDB Manager
     * @param isMarried Marital status of the HDB Manager
     */
    public HDBManager(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried, Role.HDB_MANAGER);
        this.createdProjects = new ArrayList<>();
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void addCreatedProject(Project p) {
        this.createdProjects.add(p);
    }
}
