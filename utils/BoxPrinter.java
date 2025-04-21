/**
 * A utility class for printing formatted text in bordered boxes.
 * Provides methods to create consistent, visually appealing box layouts in console output.
 * The box width is fixed at {@value #BOX_WIDTH} characters.
 */

package utils;

public class BoxPrinter {
    public static final int BOX_WIDTH = 74;

    /**
     * Prints the top border of a box using box-drawing characters.
     * The border consists of '╔', '═', and '╗' characters.
     */
    public static void printTopBorder() {
        System.out.println("╔" + "═".repeat(BOX_WIDTH - 2) + "╗");
    }

    /**
     * Prints the bottom border of a box using box-drawing characters.
     * The border consists of '╚', '═', and '╝' characters.
     */
    public static void printBottomBorder() {
        System.out.println("╚" + "═".repeat(BOX_WIDTH - 2) + "╝");
    }

    /**
     * Prints a horizontal divider for use inside a box.
     * The divider consists of '╠', '═', and '╣' characters.
     * Useful for separating sections within the same box.
     */
    public static void printDivider() {
        System.out.println("╠" + "═".repeat(BOX_WIDTH - 2) + "╣");
    }

    /**
     * Prints a formatted row with a label-value pair inside the box.
     * The row follows the format: "║ LABEL | VALUE ║" with fixed spacing.
     *
     * @param label The left-aligned label text (max 25 characters before truncation)
     * @param value The left-aligned value text (max 42 characters before truncation)
     */
    public static void printRow(String label, String value) {
        System.out.printf("║ %-25s | %-42s ║\n", label, value);
    }

    /**
     * Centers text within the box borders with equal padding on both sides.
     * If the text length is odd, the extra space is added to the right padding.
     *
     * @param text The text to center in the box
     * @return A formatted string with the text centered between box borders
     * @throws IllegalArgumentException if the text length exceeds {@value #BOX_WIDTH} - 2
     */
    public static String centerInBox(String text) {
        int padding = (BoxPrinter.BOX_WIDTH - 2 - text.length());
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return "║" + " ".repeat(leftPadding) + text + " ".repeat(rightPadding) + "║";
    }
}
