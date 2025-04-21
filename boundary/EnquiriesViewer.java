/**
 * This class is responsible for displaying enquiry information in a formatted box layout.
 * Formats and presents enquiries with project details, applicant information (with masked NRIC),
 * and the enquiry/answer content.
 */

package boundary;

import utils.BoxPrinter;
import java.util.List;
import entity.*;

public class EnquiriesViewer {
    /**
     * Prints a list of enquiries in a visually formatted box layout.
     * Each enquiry is separated by dividers, with the following information displayed:
     * - Enquiry number (1-based index)
     * - Project name
     * - Applicant name with masked NRIC (last 4 digits visible)
     * - Enquiry message
     * - Answer to the enquiry
     *
     * @param enquiries The list of Enquiry objects to display
     * @throws NullPointerException if the enquiries list is null
     */
    public static void printEnquiries(List<Enquiry> enquiries) {
        BoxPrinter.printTopBorder();

        for (int i = 0; i < enquiries.size(); i++) {
            Enquiry enquiry = enquiries.get(i);
            BoxPrinter.printRow("Enquiry Number:", String.valueOf(i+1));
            BoxPrinter.printRow("Project:", enquiry.getProject().getProjectName());
            String nric = enquiry.getApplicant().getNric();
            String maskedNric = "****" + nric.substring(nric.length() - 4);
            String nameWithMaskedNric = enquiry.getApplicant().getName() + " (" + maskedNric + ")";
            BoxPrinter.printRow("Applicant:", nameWithMaskedNric);
            BoxPrinter.printRow("Message: ", enquiry.getMessage());
            BoxPrinter.printRow("Answer: ", enquiry.getAnswer());

            if (i < enquiries.size() - 1) {
                BoxPrinter.printDivider(); // Inner separator
            } else {
                BoxPrinter.printBottomBorder(); // End of final enquiry
            }
        }
        
    }
}
