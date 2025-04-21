/* Class is for storage and access to list of all enquiries in the database */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class EnquiryService {
    private static IEnquiryRepository enquiryRepository;
    private static List<Enquiry> enquiryList = new ArrayList<>();

    public static boolean startEnquiryStorage(IEnquiryRepository repo) {
        enquiryRepository = repo;
        enquiryList.clear();
        enquiryList = enquiryRepository.loadAllEnquiries();
        //assign enquiries to applicants and projects
        for(Enquiry en: enquiryList){
            en.getApplicant().addEnquiry(en);
            en.getProject().addEnquiry(en);
        }
        return true;
    }

    public static List<Enquiry> getAllEnquiries() {
        return enquiryList;
    }

    public static void updateEnquiries(){
        enquiryRepository.saveAllEnquiries(enquiryList);
    }

    public static void addEnquiry(Enquiry e) {
        enquiryList.add(e);
    }

    public static void removeEnquiry(Enquiry e) {
        enquiryList.remove(e);
    }
}