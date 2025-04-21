/**
 * This class represents an applicant in the BTO Management System.
 *
 * @author Michelle Aye
 * @version 1.0
 * @since 2025-04-21
 */

package entity;

import java.util.ArrayList;
import java.util.List;

import repository.ApplicationService;
import utils.*;

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

    public Application getApplication() {
        return application;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }
}