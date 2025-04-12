/* This class provides the UI for the Welcome Page */
import java.util.Scanner;

public class Welcome {
    public void welcome() {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
        ██████╗ ████████╗ ██████╗     ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗███╗   ███╗███████╗███╗   ██╗████████╗    ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗
        ██╔══██╗╚══██╔══╝██╔═══██╗    ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝    ██╔════╝╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔════╝████╗ ████║
        ██████╔╝   ██║   ██║   ██║    ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║       ███████╗ ╚████╔╝ ███████╗   ██║   █████╗  ██╔████╔██║
        ██╔══██╗   ██║   ██║   ██║    ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║       ╚════██║  ╚██╔╝  ╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║
        ██████╔╝   ██║   ╚██████╔╝    ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║       ███████║   ██║   ███████║   ██║   ███████╗██║ ╚═╝ ██║
        ╚═════╝    ╚═╝    ╚═════╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝       ╚══════╝   ╚═╝   ╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝                                                                                                                                                                              
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
                    BTOSystem system = new BTOSystem(); // Initialize your system here
                    Login login = new Login(system.getUsers()); // Pass users from the system
                    login.login(); // Call login method
                    break;
                case 2: 
                    Exit exit = new Exit();
                    exit.exit();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }
}