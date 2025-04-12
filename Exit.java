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