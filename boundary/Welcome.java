/**
 * This class Provides the user interface for the application's welcome page and main menu.
 * This directs users to the login page, or the exit page to quit the application.
 */

package boundary;

import java.util.Scanner;

import entity.UserSession;
import utils.ClearPage;

public class Welcome {
    /**
     * Displays the welcome screen and main menu, then handles user selection.
     * Based on user selection, it either initiates the login process via {@link Login} class
     * or triggers application exit via {@link Exit} class
     * The method will continue prompting until a valid choice is made.
     */
    public void welcome() {
        ClearPage.clearPage();
        Scanner sc = new Scanner(System.in);
        System.out.println("""
            ______ _____ _____  ___  ___                                                  _     _____           _                
            | ___ \\_   _|  _  | |  \\/  |                                                 | |   /  ___|         | |               
            | |_/ / | | | | | | | .  . | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_  \\ `--. _   _ ___| |_ ___ _ __ ___ 
            | ___ \\ | | | | | | | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|  `--. \\ | | / __| __/ _ \\ '_ ` _ \\
            | |_/ / | | \\ \\_/ / | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_  /\\__/ / |_| \\__ \\ ||  __/ | | | | |
            \\____/  \\_/  \\___/  \\_|  |_/\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__| \\____/ \\__, |___/\\__\\___|_| |_| |_|
                                                          __/ |                                       __/ |                     
                                                         |___/                                       |___/                      
            """);

        System.out.println("""
            ╔════════════════════════════╗
            ║         MAIN MENU          ║
            ╠════════════════════════════╣
            ║  1. Log in                 ║
            ║  2. Exit                   ║
            ╚════════════════════════════╝
            """);
        int choice;
        while (true) {
            System.out.println("➤ Enter your choice (1 or 2): ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: 
                    Login login = new Login(); // Pass users from the system
                    UserSession session = login.login(); // Call login method and get session
                    if (session != null) {
                        login.startUserSession(session); // Start the correct menu based on role
                    }
                    return;
                case 2: 
                    Exit exit = new Exit();
                    exit.exit();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }
}