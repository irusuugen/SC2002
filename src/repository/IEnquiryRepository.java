/**
 * Defines the interface for enquiry data persistence.
 *
 * This abstraction allows for different storage implementations (e.g., CSV, database)
 * without changing how enquiries are used throughout the system.
 *
 * Used by the {@link EnquiryService} to load and save enquiry data.
 *
 */


package repository;

import entity.Application;
import entity.Enquiry;
import java.util.List;

public interface IEnquiryRepository {
    /**
     * Loads all enquiries from the storage medium.
     *
     * @return A list of all {@link Enquiry} instances.
     */
    List<Enquiry> loadAllEnquiries();

    /**
     * Saves the full list of enquiries to the storage medium.
     *
     * @param enquiries The list of enquiries to be saved.
     */
    void saveAllEnquiries(List<Enquiry> enquiries);
}