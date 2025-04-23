package control;

import entity.Enquiry;
import entity.Project;
import entity.User;

import java.util.List;

public interface IEnquiryReplyService {
    List<Enquiry> getUnrepliedEnquiries(Project project);
    void viewUnrepliedEnquiries(Project project);
    void replyEnquiry(Enquiry enquiry);
}
