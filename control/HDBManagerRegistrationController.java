package control;

import entity.*;
import java.util.*;

public class HDBManagerRegistrationController {
    private Scanner sc = new Scanner(System.in);

    public void handleRegistration(HDBManager manager, boolean approve) {
        System.out.print("Enter project name: ");
        String name = sc.nextLine();
        for (Project project : manager.getCreatedProjects()) {
            if (project.getProjectName().equalsIgnoreCase(name)) {
                List<Registration> regs = project.getRegistrations();
                for (int i = 0; i < regs.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, regs.get(i).getRegisteredOfficer().getName());
                }
                int idx = IntGetter.readInt("Select officer: ") - 1;
                Registration reg = regs.get(idx);
                if (approve) {
                    if (project.checkOfficerSlot()) {
                        project.addOfficer(reg.getRegisteredOfficer());
                        project.unregisterOfficer(reg);
                        System.out.println("Officer approved.");
                    } else {
                        System.out.println("No slots available.");
                    }
                } else {
                    project.unregisterOfficer(reg);
                    System.out.println("Officer rejected.");
                }
            }
        }
    }

    public void approveWithdrawal(List<Application> allApplications) {
        System.out.print("Enter applicant NRIC to approve withdrawal: ");
        String nric = sc.nextLine();
        for (Application app : allApplications) {
            if (app.getApplicant().getNric().equalsIgnoreCase(nric)) {
                app.withdraw();
                app.getProject().removeOccupiedFlat(app.getFlatType());
                System.out.println("Withdrawal approved.");
            }
        }
    }
}
