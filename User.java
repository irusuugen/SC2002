public abstract class User {
    private String name;
    private String nric;
    private String password;
    private int age;
    private boolean isMarried;


    public User(String name, String nric, String password, int age, boolean isMarried) {
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.isMarried = isMarried;
        this.name = name;
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
}