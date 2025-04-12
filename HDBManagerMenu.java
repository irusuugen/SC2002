public class HDBManagerMenu {
    public static void hdbManagerMenu(User user) {
        ClearPage.clearPage();
        System.out.println("Hello, " + user.getName() + "!\n");
        System.out.println("""
        ╔════════════════════════════════════════════╗
        ║             HDB Manager Menu               ║
        ╠════════════════════════════════════════════╣
        ║  1. Change password                        ║
        ║  2. View all open projects                 ║
        ║  3. Apply for a project                    ║
        ║  4. View application                       ║
        ║  5. Book flat                              ║
        ║  6. Request withdrawal for application     ║
        ║  7. Submit enquiry about a project         ║
        ║  8. View enquiries                         ║
        ║  9. Edit enquiries                         ║
        ║  10. Delete enquiries                      ║
        ║  11. Logout                                ║
        ╚════════════════════════════════════════════╝
        """);
        System.out.println("");
    }
}