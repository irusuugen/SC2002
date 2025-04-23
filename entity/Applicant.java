/**
 * This class represents an applicant in the BTO Management System.
 *
 *
 */

package entity;

import java.util.ArrayList;
import java.util.List;

public class Applicant extends User {
    private Application application;
    private List<Enquiry> enquiries;

    /**
     * Creates a new applicant with basic information
     *
     *
     */
    public Applicant(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried, Role.APPLICANT);
        this.enquiries = new ArrayList<>();
        this.application = null;
    }

    public Applicant(String name, String nric, String password, int age, boolean isMarried, Role role) {
        super(name, nric, password, age, isMarried, role);
        this.enquiries = new ArrayList<>();
        this.application = null;
    }

    /**
     * Checks whether the applicant is eligible for a project.
     * Eligibility is based on the number of two-room flats available if the applicant is single,
     * or the number of available two and three-room flats if the applicant is married.
     *
     * @param project The project that the applicant is being checked for their eligibltiy for
     * @return true if applicant is eligible for the project
     */
    @Override
    public boolean isEligibleForProject(Project project) {
        if (getUserGroup() == UserGroup.MARRIED) {
            return project.getNumFlatAvailable(FlatType.TWOROOMS) > 0 ||
                    project.getNumFlatAvailable(FlatType.THREEROOMS) > 0;
        } else if (getUserGroup() == UserGroup.SINGLE) {
            return project.getNumFlatAvailable(FlatType.TWOROOMS) > 0;
        }
        return false;
    }

    /**
     * Returns the application associated with the applicant.
     *
     * @return The applicant's application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Returns the list of enquiries associated with the applicant
     *
     * @return The list of enquiries made
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Associates the application made with the applicant
     *
     * @param application The application to associate the applicant with
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Adds the enquiry to the list of enquiries associated with the applicant
     *
     * @param enquiry The enquiry made by the applicant
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }
}