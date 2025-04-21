/**
 * This class is responsible for displaying application information in a formatted manner.
 * Uses {@link utils.BoxPrinter} to present application data in visually appealing box layouts.
 */

package boundary;

import entity.*;
import utils.BoxPrinter;

import java.util.List;

public class ApplicationViewer {

    /**
     * Prints the details of a single application in a bordered box format.
     * Includes applicant information, application details, and project information.
     *
     * @param application The application object to display
     */
    public static void printApplication(Application application) {
        BoxPrinter.printTopBorder();
        showApplicationDetails(application);
        BoxPrinter.printBottomBorder();
    }

    /**
     * Prints a list of applications with separators between each application.
     * Displays a message if the list is empty.
     *
     * @param applications The list of applications to display
     */
    public static void printApplications(List<Application> applications) {
        if (applications.isEmpty()) {
            System.out.println("No applications available to display.");
            return;
        }

        System.out.println("Here is the list of applications:");
        BoxPrinter.printTopBorder();
        for (int i = 0; i < applications.size(); i++) {
            Application application = applications.get(i);
            BoxPrinter.printRow("Application No.", String.valueOf(i));
            showApplicationDetails(application);
            if (i < applications.size() - 1) {
                BoxPrinter.printDivider();
            } else {
                BoxPrinter.printBottomBorder();
            }
        }
    }

    /**
     * Displays detailed information about an application that is within the box layout.
     * Includes masked NRIC for privacy, applicant details, and application status.
     * Automatically shows withdrawal status if applicable.
     *
     * @param application The application object whose details to display
     */
    public static void showApplicationDetails(Application application) {
        String nric = application.getApplicant().getNric();
        String maskedNric = "****" + nric.substring(nric.length() - 4);
        BoxPrinter.printRow("Applicant", (application.getApplicant().getName() + " (" + maskedNric + ")"));
        BoxPrinter.printRow("Age", String.valueOf(application.getApplicant().getAge()));
        BoxPrinter.printRow("Marital Status", application.getApplicant().isMarried() ? "Married" : "Single");
        BoxPrinter.printRow("Application Date", application.getDate().toString() );
        BoxPrinter.printRow("Flat Type Applied For", application.getFlatType().toString());
        BoxPrinter.printRow("Application Status", application.getStatus().toString() + ((application.isWithdrawalRequested() && application.getStatus()!=Status.UNSUCCESSFUL)? " (Withdrawal Requested)" : ""));
        BoxPrinter.printDivider();
        ProjectViewer.filterProjectDetailsForApplicants(application.getProject(), application.getApplicant());
    }
}
