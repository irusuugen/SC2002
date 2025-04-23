package control;

import entity.Enquiry;
import entity.HDBOfficer;

public interface IOfficerEnquiryService extends IEnquiryReplyService {
    void viewEnquiries(HDBOfficer officer);
    Enquiry selectAssignedProjectEnquiry(HDBOfficer officer);
}
