package entity;

public abstract class User {
    private String name;
    private String nric;
    private String password;
    private int age;
    private boolean isMarried;
    private UserGroup userGroup;
    private final Role role;

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
        } else {
            userGroup = UserGroup.NEITHER;
        }
    }

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