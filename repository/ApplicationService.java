/* Class is for storage and access to list of all application in the database */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private static IApplicationRepository applicationRepository;
    private static List<Application> applicationList = new ArrayList<>();

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

    public static List<Application> getAllApplications() {
        return applicationList;
    }

    public static Application fetchApplicationFromNRIC(String NRIC){
        Application application = null;
        for(Application app: applicationList){
            if(app.getApplicant().getNric().equals(NRIC)) application = app;
        }
		return application;
	}

    public static void updateApplications(){
        applicationRepository.saveAllApplications(applicationList);
    }

    public static void addApplication(Application a) {
        applicationList.add(a);
    }

    public static void removeApplication(Application a) {
        applicationList.remove(a);
    }
}