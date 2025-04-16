package utils;

public class BoxPrinter {
    public static final int BOX_WIDTH = 74;

    public static void printTopBorder() {
        System.out.println("╔" + "═".repeat(BOX_WIDTH - 2) + "╗");
    }

    public static void printBottomBorder() {
        System.out.println("╚" + "═".repeat(BOX_WIDTH - 2) + "╝");
    }

    public static void printDivider() {
        System.out.println("╠" + "═".repeat(BOX_WIDTH - 2) + "╣");
    }

    public static void printRow(String label, String value) {
        System.out.printf("║ %-25s | %-42s ║\n", label, value);
    }
}
