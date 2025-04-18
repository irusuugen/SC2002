package entity;

import java.util.ArrayList;
import java.util.List;

import repository.ApplicationService;
import utils.*;

public class Applicant extends User {
    private Application application;
    private List<Enquiry> enquiries;

    public Applicant(String name, String nric, String password, int age, boolean isMarried) {
        super(name, nric, password, age, isMarried);
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