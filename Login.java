/* This class provides the UI for role selection and logging in */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Login {
    private List<User> users;

    public Login(List<User> users) {
        this.users = users;
    }

    // Overall UI
    public void login() {
        ClearPage.clearPage();
        Role userRole = roleSelection(); // Carries out UI for role selection and stores the role
        
        // Carry out UI for logging in
        ClearPage.clearPage();
        System.out.println("""
        ╔═══════════════════════════════════════╗
        ║             Login Portal              ║
        ╚═══════════════════════════════════════╝
        """);
        Scanner sc = new Scanner(System.in);
        User user = null;
        while (true) {
            System.out.print("Enter NRIC: ");
            String nric = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            try {
                user = validate(nric, password);
                System.out.println("Login succesful! Welcome, " + user.getName());
                break;
            } catch (Exception e) {
                System.err.print(e.getMessage());
                System.out.println(" Please try again.");
            }
        }

        // Direct to menu based on user's role
        switch (userRole) {
            case APPLICANT:
                ApplicantMenu.applicantMenu(user);
                break;
            case HDB_OFFICER:
                HDBOfficerMenu.hdbOfficerMenu(user);
                break;
            case HDB_MANAGER:
                HDBManagerMenu.hdbManagerMenu(user);
                break;
        }
    }

    // UI for role selection
    public Role roleSelection() {
        System.out.println("""
        ╔═══════════════════════════════════════╗
        ║           Select Your Role            ║
        ╠═══════════════════════════════════════╣
        ║  1. Applicant                         ║
        ║  2. HDB Officer                       ║
        ║  3. HDB Manager                       ║
        ╚═══════════════════════════════════════╝
        """);
        System.out.println("➤ Select your role (1-3): ");
        int choice;
        Scanner sc = new Scanner(System.in);
            while (true) {
                choice = sc.nextInt();
            switch (choice) {
                case 1:
                    return Role.APPLICANT;
                case 2:
                    return Role.HDB_OFFICER;
                case 3:
                    return Role.HDB_MANAGER;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Checks for the user details in the user database and returns the user
    public User validate(String nric, String password) {
        // Check for valid NRIC format
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            throw new IllegalArgumentException("Invalid NRIC.");
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