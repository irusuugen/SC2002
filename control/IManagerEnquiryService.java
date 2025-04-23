package control;

import entity.Enquiry;
import entity.HDBManager;
import entity.Project;
import java.util.List;

public interface IManagerEnquiryService extends IEnquiryReplyService {
    void viewAllEnquiries(List<Project> allProjects);
    Enquiry selectEnquiry(HDBManager manager, IManagerProjectService projectService);
}
