/**
 * Interface for enquiry-related services available to Applicants.
 */
package control;

import entity.Applicant;
import entity.Enquiry;

public interface IApplicantEnquiryService {
    /**
     * Allows the applicant to submit a new enquiry.
     *
     * @param applicant Applicant submitting the enquiry
     * @param applicantProjectService Service used to choose a project for the enquiry
     */
    void submitEnquiry(Applicant applicant, IApplicantProjectService applicantProjectService);

    /**
     * Displays all enquiries submitted by the applicant.
     *
     * @param applicant Applicant viewing their enquiries
     */
    void viewEnquiries(Applicant applicant);

    /**
     * Allows the applicant to edit one of their existing enquiries.
     *
     * @param applicant Applicant editing their enquiry
     */
    void editEnquiry(Applicant applicant);

    /**
     * Allows the applicant to delete one of their existing enquiries.
     *
     * @param applicant Applicant deleting their enquiry
     */
    void deleteEnquiry(Applicant applicant);

    /**
     * Allows the applicant to select one of their existing enquiries.
     *
     * @param applicant Applicant selecting an enquiry
     * @return Enquiry selected
     */
    Enquiry selectEnquiry(Applicant applicant);
}
