/**
 * This class is responsible for printing the goodbye message and exiting out of the program.
 */

package boundary;

import utils.*;
public class Exit {
    public void exit() {
        ClearPage.clearPage();
        System.out.println("Thank you for using our system.");
        System.out.println("""
        ▗▄▄▖ ▗▄▖  ▗▄▖ ▗▄▄▄ ▗▄▄▖▗▖  ▗▖▗▄▄▄▖
       ▐▌   ▐▌ ▐▌▐▌ ▐▌▐▌  █▐▌ ▐▌▝▚▞▘ ▐▌   
       ▐▌▝▜▌▐▌ ▐▌▐▌ ▐▌▐▌  █▐▛▀▚▖ ▐▌  ▐▛▀▀▘
       ▝▚▄▞▘▝▚▄▞▘▝▚▄▞▘▐▙▄▄▀▐▙▄▞▘ ▐▌  ▐▙▄▄▖                                 
       """);
        System.exit(0);
    }
}