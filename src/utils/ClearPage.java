/**
 * This is a utility class that allows for the clearing of the terminal
 */
package utils;

public class ClearPage {
    /**
     * Clears the terminal in a different way based on the external user's
     * operating system.
     */
    public static void clearPage() {
        String os = System.getProperty("os.name"); // Detects OS being used
        try {
            // Clearing terminal for Windows
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // Clearing terminal for Unix/Linux/MacOS
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush(); // Ensures above line is executed
            }
        } catch (Exception e) {
            // Failure to clear screen. No action taken and the system continues as per normal without clearing.
        }
    }
}