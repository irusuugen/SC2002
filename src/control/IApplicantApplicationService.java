/**
 * Provides the interface for application-related services for an applicant.
 */

package control;

import entity.Applicant;

public interface IApplicantApplicationService {

    /**
     * Allows applicant to apply for project
     * @param applicant Applicant applying for project
     * @param applicantProjectService Project service used to retrieve projects
     */
    void applyForProject(Applicant applicant, IApplicantProjectService applicantProjectService);

    /**
     * Prints out application details if available
     * @param applicant Applicant viewing their application
     */

    void viewApplication(Applicant applicant);

    /**
     * Allows applicant to make booking request after successful application
     * @param applicant Applicant requesting a booking
     */
    void requestBooking(Applicant applicant);

    /**
     * Allows applicant to make withdrawal request regardless of application status (unless unsuccessful)
     * @param applicant Applicant making a withdrawal request
     */
    void requestWithdrawal(Applicant applicant);
}
