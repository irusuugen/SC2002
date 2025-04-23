package control;

import entity.Applicant;
import entity.Enquiry;

public interface IApplicantEnquiryService {
    void submitEnquiry(Applicant applicant, IApplicantProjectService applicantProjectService);
    void viewEnquiries(Applicant applicant);
    void editEnquiry(Applicant applicant);
    void deleteEnquiry(Applicant applicant);
    Enquiry selectEnquiry(Applicant applicant);
}
