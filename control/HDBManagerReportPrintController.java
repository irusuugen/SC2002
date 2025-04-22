/**
 * Allows the maanger to print a report of applications made. The manager is able to filter
 * and choose between the user groups, as well as the type of room.
 */

package control;

import entity.*;
import boundary.ApplicationViewer;

import java.util.*;

public class HDBManagerReportPrintController {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Generates and prints a report of applications filtered by user group (Married/Single/All)
     * and flat type (2-Room/3-Room/All).
     *
     * @param allApplications List of all applications to filter from.
     */
    public static void generateAndPrintReport(List<Application> allApplications) {
        System.out.print("Filter by user group (Married/Single/All): ");
        String groupFilter = sc.nextLine().trim();

        System.out.print("Filter by flat type (2-Room/3-Room/All): ");
        String flatFilter = sc.nextLine().trim();

        List<Application> result = new ArrayList<>();

        for (Application app : allApplications) {
            boolean matchesGroup = groupFilter.equalsIgnoreCase("all")
                    || (groupFilter.equalsIgnoreCase("married") && app.getApplicant().isMarried())
                    || (groupFilter.equalsIgnoreCase("single") && !app.getApplicant().isMarried());

            boolean matchesFlat = flatFilter.equalsIgnoreCase("all")
                    || (flatFilter.equalsIgnoreCase("2-room") && app.getFlatType() == FlatType.TWOROOMS)
                    || (flatFilter.equalsIgnoreCase("3-room") && app.getFlatType() == FlatType.THREEROOMS);

            if (matchesGroup && matchesFlat) {
                result.add(app);
            }
        }

        if (result.isEmpty()) {
            System.out.println("No applications found.");
        } else {
            for (Application app : result) {
                System.out.println();
                ApplicationViewer.printApplication(app);
            }
        }
    }
}
