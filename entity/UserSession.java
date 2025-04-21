/**
 * This class represents the session state of a logged-in user during their interaction
 * with the BTO Management System.
 *
 * A session stores both the authenticated User, and their current filtering
 * settings for the display of projects. The session instance is passed
 * between boundary classes (like menus) to provide consistent access to
 * session-specific data.
 *
 * @author Iniya Murali
 * @version 1.0
 * @since 2025-04-21
 *
 * @see User
 * @see ProjectFilter
 */


package entity;

public class UserSession {
    private User user;
    private ProjectFilter projectFilter;

    /**
     * Constructs a new UserSession for the specific user.
     * A new ProjectFilter is also initialized for a new session.
     *
     * @param user The authenticated user to associate with the session.
     */
    public UserSession(User user) {
        this.user = user;
        this.projectFilter = new ProjectFilter();
    }

    /**
     * Returns the user associated with this session.
     *
     * @return The current user.
     */
    public User getUser() { return user; }

    /**
     * Returns the project filters associated with this session.
     *
     * @return The current ProjectFilter settings.
     */
    public ProjectFilter getProjectFilter() { return projectFilter; }
}