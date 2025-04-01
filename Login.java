import java.util.*;

public class Login {
    private ArrayList<User> users;

    public Login(ArrayList<User> users) {
        this.users = users;
    }

    public void startLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Login ===");
        while (true) {
            System.out.print("Enter NRIC: ");
            String nric = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            try {
                User user = validate(nric, password);
                System.out.println("Login succesful! Welcome, " + user.getName());
                break;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    public User validate(String nric, String password) {
        // Check for valid NRIC format
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            throw new IllegalArgumentException("Invalid NRIC!");
        }
        // Check for NRIC in database
        User user = users.stream()
            .filter(u -> u.getNric().equals(nric))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Verify password
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        return user;
    }

}