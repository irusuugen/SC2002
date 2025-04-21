package repository;

import entity.Enquiry;
import java.util.List;

public interface IEnquiryRepository {
    List<Enquiry> loadAllEnquiries();
    void saveAllEnquiries(List<Enquiry> enquiries);
}