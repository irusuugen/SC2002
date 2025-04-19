package entity;

public class UserSession {
    private User user;
    private ProjectFilter projectFilter;

    public UserSession(User user) {
        this.user = user;
        this.projectFilter = new ProjectFilter();
    }

    public User getUser() { return user; }
    public ProjectFilter getProjectFilter() { return projectFilter; }
}