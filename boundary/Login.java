package boundary;

import entity.*;
import java.util.Scanner;
import repository.*;
import utils.*;

public class Login {
    private final Scanner sc;

    public Login() {
        this.sc = new Scanner(System.in);
    }

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

        User user = null;
        while (user == null) {
            System.out.print("Enter NRIC: ");
            String nric = sc.nextLine();
            if (nric.trim().isEmpty()) {
                System.out.println("NRIC cannot be empty. Please try again.");
                continue;
            }

            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            if (password.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
                continue;
            }

            try {
                user = validate(nric, password);
                System.out.println("Login successful! Welcome, " + user.getName());

                // Adds a delay before moving to the next page
                try {
                    Thread.sleep(1000); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; // Break out of the loop after successful login
            } catch (Exception e) {
                System.err.print(e.getMessage());
                System.out.println(" Please try again.");
            }
        }

        // Direct to menu based on user's role
        switch (userRole) {
            case APPLICANT:
                ApplicantMenu.applicantMenu((Applicant) user);
                return;
            case HDB_OFFICER:
                HDBOfficerMenu.hdbOfficerMenu((HDBOfficer) user);
                return;
            case HDB_MANAGER:
                HDBManagerMenu.hdbManagerMenu((HDBManager) user);
                return;
        }
    }

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
        while (true) {
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
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

    public User validate(String nric, String password) {
        // Check for valid NRIC format
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            throw new IllegalArgumentException("Invalid NRIC.");
        }

        // Check for NRIC in database
        User user = UserService.getAllUsers().stream()
            .filter(u -> u.getNric().equals(nric))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Verify password
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        return user; // Returns the logged-in user
    }
}
