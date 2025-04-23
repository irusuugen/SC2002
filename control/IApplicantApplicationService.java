package control;

import entity.Applicant;

public interface IApplicantApplicationService {
    void applyForProject(Applicant applicant, IApplicantProjectService applicantProjectService);
    void viewApplication(Applicant applicant);
    void requestBooking(Applicant applicant);
    void requestWithdrawal(Applicant applicant);
}
