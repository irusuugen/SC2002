package boundary;

import utils.BoxPrinter;
import java.util.List;
import entity.*;

public class EnquiriesViewer {
    public static void printEnquiries(List<Enquiry> enquiries) {
        System.out.println("Here is a list of your enquiries:");
        BoxPrinter.printTopBorder();

        for (int i = 0; i < enquiries.size(); i++) {
            Enquiry enquiry = enquiries.get(i);
            BoxPrinter.printRow("Enquiry Number:", String.valueOf(i+1));
            BoxPrinter.printRow("Project:", enquiry.getProject().getProjectName());
            BoxPrinter.printRow("Message: ", enquiry.getMessage());
            BoxPrinter.printRow("Answer: ", enquiry.getAnswer());

            if (i < enquiries.size() - 1) {
                BoxPrinter.printDivider(); // Inner separator
            } else {
                BoxPrinter.printBottomBorder(); // End of final project
            }
        }
        
    }
                
        
}
