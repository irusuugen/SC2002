/**
 * Interface for handling enquiries assigned to HDB Officers.
 * Extends {@link IEnquiryReplyService} to provide reply functionality.
 */

package control;

import entity.Enquiry;
import entity.HDBOfficer;

public interface IOfficerEnquiryService extends IEnquiryReplyService {
    /**
     * Displays all enquiries assigned to the given officer.
     *
     * @param officer the HDB Officer whose enquiries are to be viewed
     */
    void viewEnquiries(HDBOfficer officer);

    /**
     * Allows the officer to select a specific enquiry related to their assigned project.
     *
     * @param officer the HDB Officer making the selection
     * @return the selected {@link Enquiry}
     */
    Enquiry selectAssignedProjectEnquiry(HDBOfficer officer);
}
