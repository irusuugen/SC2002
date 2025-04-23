/**
 * This is a utility class that allows for checking
 * of whether the application periods of two projects clash
 */

package utils;

import java.time.LocalDate;
import entity.*;

public class DateOverlap {
    /**
     * Checks for clashing in application periods between two projects
     *
     * @param p1 The first project used for comparison
     * @param p2 The second project used for comparison
     * @return True if the application periods of the projects clash
     */
    public static boolean applicationPeriodsOverlap(Project p1, Project p2) {
        LocalDate start1 = p1.getOpenDate();
        LocalDate end1 = p1.getCloseDate();
        LocalDate start2 = p2.getOpenDate();
        LocalDate end2 = p2.getCloseDate();

        return !end1.isBefore(start2) && !end2.isBefore(start1);
    }

}
