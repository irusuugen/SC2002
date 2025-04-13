import boundary.*;
import control.*;

public class Main {
    public static void main(String[] args) {
        // System setup
        BTOCoordinator bto = new BTOCoordinator();

        // Start the welcome screen
        Welcome welcome = new Welcome(bto);
        welcome.welcome();
    }
}
