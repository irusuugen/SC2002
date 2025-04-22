/**
 * Abstract class representing a user in the system.
 * A user can be of different roles: APPLICANT, HDB_OFFICER, or HDB_MANAGER.
 * The class includes user details like name, NRIC, age, marital status, and role.
 */

package entity;

public abstract class User {
    private String name;
    private String nric;
    private String password;
    private int age;
    private boolean isMarried;
    private UserGroup userGroup;
    private final Role role;

    /**
     * Constructs a new User with the specified details.
     * The user group is determined based on the user's age and marital status.
     *
     * @param name      Name of the user
     * @param nric      NRIC of the user
     * @param password  Password of the user
     * @param age       Age of the user
     * @param isMarried Marital status of the user
     * @param role      Role of the user (APPLICANT, HDB_OFFICER, or HDB_MANAGER)
     */
    public User(String name, String nric, String password, int age, boolean isMarried, Role role) {
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.isMarried = isMarried;
        this.name = name;
        this.role = role;
        
        if (age >= 35 && !isMarried) {
            userGroup = UserGroup.SINGLE;
        } else if (age >= 21 && isMarried) {
            userGroup = UserGroup.MARRIED;
        }
    }

    /**
     * Determines if the user is eligible for the specified project.
     *
     * @param project Project to check eligibility for
     * @return True if the user is eligible for the project, false otherwise
     */
    public boolean isEligibleForProject(Project project) {
        return true;
    }
    public Role getRole() {
        return this.role;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public String getNric() {
        return this.nric;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }
    
    public boolean isMarried() {
        return this.isMarried;
    }

    public int getAge() {
        return this.age;
    }

    public UserGroup getUserGroup() {
        return this.userGroup;
    }
}