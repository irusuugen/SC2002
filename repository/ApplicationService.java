/**
 * Provides high-level access to all application-related operations.
 *
 * This service is responsible for:
 * <ul>
 *     <li>Loading applications from the data repository</li>
 *     <li>Assigning applications to applicants and projects</li>
 *     <li>Retrieving, adding, and removing applications</li>
 *     <li>Saving updates to persistent storage</li>
 * </ul>
 *
 */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private static IApplicationRepository applicationRepository;
    private static List<Application> applicationList = new ArrayList<>();

    /**
     * Initializes the application repository and loads all applications.
     * Each application is also linked to its corresponding applicant and project.
     *
     * @param repo The data repository to use.
     * @return {@code true} if storage initialization succeeds.
     */
    public static boolean startApplicationStorage(IApplicationRepository repo) {
        applicationRepository = repo;
        applicationList.clear();
        applicationList = applicationRepository.loadAllApplications();
        //assign application to applicants and projects
        for (Application app: applicationList){
            app.getApplicant().setApplication(app);
            app.getProject().addApplication(app);
        }
        return true;
    }

    /**
     * Retrieves the full list of loaded applications.
     *
     * @return The list of all {@link Application} instances.
     */
    public static List<Application> getAllApplications() {
        return applicationList;
    }

    /**
     * Searches for and retrieves an application using the applicant's NRIC.
     *
     * @param NRIC The NRIC of the applicant.
     * @return The matching application, or {@code null} if not found.
     */
    public static Application fetchApplicationFromNRIC(String NRIC){
        Application application = null;
        for(Application app: applicationList){
            if(app.getApplicant().getNric().equals(NRIC)) application = app;
        }
		return application;
	}

    /**
     * Saves the current application list to persistent storage.
     */
    public static void updateApplications(){
        applicationRepository.saveAllApplications(applicationList);
    }

    /**
     * Adds a new application to the internal list.
     *
     * @param a The application to add.
     */
    public static void addApplication(Application a) {
        applicationList.add(a);
    }

    /**
     * Removes an existing application from the internal list.
     *
     * @param a The application to remove.
     */
    public static void removeApplication(Application a) {
        applicationList.remove(a);
    }
}