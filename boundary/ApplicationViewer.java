package boundary;

import entity.*;
import utils.BoxPrinter;

public class ApplicationViewer {
    public static void printApplication(Application application) {

        System.out.println("Your application and project details:");
        BoxPrinter.printTopBorder();
        String nric = application.getApplicant().getNric();
        String maskedNric = "****" + nric.substring(nric.length() - 4);
        BoxPrinter.printRow("Applicant", (application.getApplicant().getName() + " (" + maskedNric + ")"));
        BoxPrinter.printRow("Age", String.valueOf(application.getApplicant().getAge()));
        BoxPrinter.printRow("Marital Status", application.getApplicant().isMarried() ? "Married" : "Single");
        BoxPrinter.printRow("Application Date", application.getDate().toString() + (application.isWithdrawalRequested() ? " (Withdrawal Requested)" : "") );
        BoxPrinter.printRow("Flat Type Applied For", application.getFlatType().toString());
        BoxPrinter.printRow("Application Status", application.getStatus().toString());
        BoxPrinter.printDivider();
        ProjectViewer.filterProjectDetailsForApplicants(application.getProject(), application.getApplicant());
        BoxPrinter.printBottomBorder();
    
    }


}
