/**
 * This class handles the complete login workflow, from
 * role selection to credential validation and session creation
 * for the different user types
 */

package boundary;

import entity.*;
import java.util.*;
import repository.*;
import utils.*;

public class Login {
    private final Scanner sc;

    /**
     * Initializes a new Login handler with a Scanner for user input.
     */
    public Login() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Executes the complete login workflow.
     * @return UserSession if authentication succeeds, null if user aborts
     */
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
                user = validate(nric, password, userRole);
                System.out.println("Login successful! Welcome, " + user.getName());
                Thread.sleep(1000);
                return new UserSession(user); // Return new UserSession with initialized filters
            } catch (Exception e) {
                System.err.print(e.getMessage());
                if(!InputHelper.confirm("Would you like to login again?")){
                    return null; //Return to welcome page
                }
            }
        }
        return null; // Fallback (should never reach here)
    }

    /**
     * Routes to the appropriate menu based on user role.
     * @param session The authenticated user session
     */
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

    /**
     * Handles user role selection via console interface.
     * @return Selected user Role enum
     */
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

    /**
     * Validates user credentials against the specified role's user repository.
     *
     * @param nric User's NRIC to validate
     * @param password User's password to verify
     * @param userRole Expected role for authorization
     * @return Authenticated User object
     * @throws IllegalArgumentException if validation fails for any reason:
     *  <ul>
     *  *   <li>Invalid NRIC</li>
     *  *   <li>User not found</li>
     *  *   <li>Password mismatch</li>
     *  * </ul>
     */
    public User validate(String nric, String password, Role userRole) throws IllegalArgumentException {
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            throw new IllegalArgumentException("Invalid NRIC.");
        }

        List<User> userList = new ArrayList<>();
        switch(userRole){
            case APPLICANT:
                userList.addAll(UserService.getApplicants());
                break;
            case HDB_OFFICER:
                userList.addAll(UserService.getOfficers());
                break;
            case HDB_MANAGER:
                userList.addAll(UserService.getManagers());
                break;
            default:
                break;
        }
        User user = userList.stream()
            .filter(u -> u.getNric().equals(nric))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.\n");
        }

        return user;
    }
}