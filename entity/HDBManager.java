package entity;

import java.util.*;

public class HDBManager extends User {
    private List<Project> createdProjects;

    public HDBManager(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried, Role.HDB_MANAGER);
        this.createdProjects = new ArrayList<>();
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }
}
