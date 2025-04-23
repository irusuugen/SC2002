/**
 * Provides high-level access to all enquiry-related operations.
 *
 * This service is responsible for:
 * <ul>
 *     <li>Loading enquiries from the data repository</li>
 *     <li> Adding, removing, and retrieving enquiries in storage </li>
 *     <li>Saving updates to persistent storage</li>
 * </ul>
 *
 */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class EnquiryService {
    private static IEnquiryRepository enquiryRepository;
    private static List<Enquiry> enquiryList = new ArrayList<>();

    /**
     * Initializes the enquiry repository and loads all enquiries.
     * Each enquiry is also linked to its corresponding applicant and project.
     *
     * @param repo The data repository to use.
     * @return {@code true} if storage initialization succeeds.
     */
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

    /**
     * Retrieves the full list of loaded enquiries.
     *
     * @return The list of all {@link Enquiry} instances.
     */
    public static List<Enquiry> getAllEnquiries() {
        return enquiryList;
    }

    /**
     * Saves the current enquiry list to persistent storage.
     */
    public static void updateEnquiries(){
        enquiryRepository.saveAllEnquiries(enquiryList);
    }

    /**
     * Adds a new enquiry to the internal list.
     *
     * @param e The enquiry to add.
     */
    public static void addEnquiry(Enquiry e) {
        enquiryList.add(e);
    }

    /**
     * Removes an existing application from the internal list.
     *
     * @param e The enquiry to remove.
     */
    public static void removeEnquiry(Enquiry e) {
        enquiryList.remove(e);
    }
}