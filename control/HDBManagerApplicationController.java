package control;

import entity.*;
import java.util.*;

public class HDBManagerApplicationController {
    private Scanner sc = new Scanner(System.in);

    public void processApplication(List<Application> allApplications, boolean approve) {
        System.out.print("Enter applicant NRIC: ");
        String nric = sc.nextLine();
        for (Application app : allApplications) {
            if (app.getApplicant().getNric().equalsIgnoreCase(nric)) {
                if (approve) {
                    Project project = app.getProject();
                    FlatType type = app.getFlatType();
                    if (project.getNumFlatAvailable(type) > 0) {
                        app.markSuccessful();
                        project.addOccupiedFlat(type);
                        System.out.println("Application approved.");
                    } else {
                        app.markUnsuccessful();
                        System.out.println("No available flats.");
                    }
                } else {
                    app.markUnsuccessful();
                    System.out.println("Application rejected.");
                }
            }
        }
    }
}
