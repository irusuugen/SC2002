package control;

import java.util.*;
import entity.*;

public class HDBManagerReportPrintController {
    public List<Application> generateReport(List<Application> allApplications, String filter) {
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
        return result;
    }
}
