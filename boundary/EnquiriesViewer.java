package boundary;

import utils.BoxPrinter;
import java.util.List;
import entity.*;

public class EnquiriesViewer {
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
