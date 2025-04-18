package control;

import entity.*;
import java.util.*;

public class HDBManagerReportPrintController {
    private Scanner sc = new Scanner(System.in);

    public void generateAndPrintReport(List<Application> allApplications) {
        System.out.print("Filter (married/single/all): ");
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
        for (Application app : result) {
            System.out.println(app);
        }
    }
}
