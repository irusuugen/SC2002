/**
 * Interface for enquiry-related services handled by HDB Managers.
 */

package control;

import entity.Enquiry;
import entity.HDBManager;
import entity.Project;
import java.util.List;

public interface IManagerEnquiryService extends IEnquiryReplyService {

    /**
     * Displays all enquiries associated with all given projects.
     *
     * @param allProjects List of all projects to view enquiries for
     */
    void viewAllEnquiries(List<Project> allProjects);

    /**
     * Allows the manager to select a specific enquiry after selecting a project to choose the enquiry from
     *
     * @param manager Mnager making the selection
     * @param projectService Project service to assist in project selection
     * @return The selected enquiry
     */
    Enquiry selectEnquiry(HDBManager manager, IManagerProjectService projectService);
}
