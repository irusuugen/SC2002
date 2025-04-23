/**
 * Interface providing basic enquiry reply functionality.
 */
package control;

import entity.Enquiry;
import entity.Project;

import java.util.List;

public interface IEnquiryReplyService {
    /**
     * Retrieves a list of unreplied enquiries for the given project.
     *
     * @param project Project whose unreplied enquiries are to be fetched
     * @return List of unreplied enquiries
     */
    List<Enquiry> getUnrepliedEnquiries(Project project);

    /**
     * Displays all unreplied enquiries for the given project.
     *
     * @param project Project whose unreplied enquiries are to be viewed
     */
    void viewUnrepliedEnquiries(Project project);

    /**
     * Allows the user to reply to the given enquiry.
     *
     * @param enquiry Enquiry to reply to
     */
    void replyEnquiry(Enquiry enquiry);
}
