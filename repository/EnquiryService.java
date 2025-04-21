/* Class is for storage and access to list of all enquiries in the database */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class EnquiryService {
    private static EnquiryRepository enquiryRepository;
    private static List<Enquiry> enquiryList = new ArrayList<>();

    public static boolean startEnquiryStorage(EnquiryRepository repo) {
        enquiryRepository = repo;
        enquiryList.clear();
        enquiryList = enquiryRepository.loadAllEnquiries();
        //assign enquiries to applicants and projects
        for(Enquiry en: enquiryList){
            en.getApplicant().getEnquiries().add(en);
            en.getProject().getEnquiries().add(en);
        }
        return true;
    }

    public static List<Enquiry> getAllEnquiries() {
        return enquiryList;
    }

    public static void updateEnquiries(){
        enquiryRepository.saveAllEnquiries(enquiryList);
    }
}