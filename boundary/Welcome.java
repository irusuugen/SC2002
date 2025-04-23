/* This class provides the UI for the Welcome Page */
package boundary;

import java.util.Scanner;

import entity.UserSession;
import utils.ClearPage;

public class Welcome {
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