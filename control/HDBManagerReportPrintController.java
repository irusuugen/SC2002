package control;

import entity.*;
import boundary.ApplicationViewer;
import utils.BoxPrinter;

import java.util.*;

public class HDBManagerReportPrintController {
    private static Scanner sc = new Scanner(System.in);

    public static void generateAndPrintReport(List<Application> allApplications) {
        System.out.print("Filter (Married/Single/All): ");
        String filter = sc.nextLine();
        List<Application> result = new ArrayList<>();

        for (Application app : allApplications) {
            if (filter.equalsIgnoreCase("married") && app.getApplicant().isMarried()) {
                result.add(app);
            } else if (filter.equalsIgnoreCase("single") && !app.getApplicant().isMarried()) {
                result.add(app);
            } else if (filter.equalsIgnoreCase("all")) {
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
