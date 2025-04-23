package utils;

import java.time.LocalDate;
import entity.*;

public class DateOverlap {
    public static boolean applicationPeriodsOverlap(Project p1, Project p2) {
        LocalDate start1 = p1.getOpenDate();
        LocalDate end1 = p1.getCloseDate();
        LocalDate start2 = p2.getOpenDate();
        LocalDate end2 = p2.getCloseDate();

        return !end1.isBefore(start2) && !end2.isBefore(start1);
    }

}
