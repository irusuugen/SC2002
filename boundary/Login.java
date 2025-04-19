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

    public UserSession login() {
        ClearPage.clearPage();
        Role userRole = roleSelection();
        
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
                Thread.sleep(1000);
                return new UserSession(user); // Return new UserSession with initialized filters
            } catch (Exception e) {
                System.err.print(e.getMessage());
                System.out.println(" Please try again.");
            }
        }
        return null; // Fallback (should never reach here)
    }

    public void startUserSession(UserSession session) {
        ClearPage.clearPage();
        switch (session.getUser().getRole()) {
            case APPLICANT:
                ApplicantMenu.applicantMenu(session);
                break;
            case HDB_OFFICER:
                HDBOfficerMenu.officerMenu(session);
                break;
            case HDB_MANAGER:
                HDBManagerMenu.managerMenu(session);
                break;
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
        while (true) {
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: return Role.APPLICANT;
                case 2: return Role.HDB_OFFICER;
                case 3: return Role.HDB_MANAGER;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public User validate(String nric, String password) {
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            throw new IllegalArgumentException("Invalid NRIC.");
        }

        User user = UserService.getAllUsers().stream()
            .filter(u -> u.getNric().equals(nric))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        return user;
    }
}