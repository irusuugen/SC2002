/* Class is for storage and access to list of all application in the database */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    private static IRegistrationRepository registrationRepository;
    private static List<Registration> registrationList = new ArrayList<>();

    public static boolean startRegistrationStorage(IRegistrationRepository repo) {
        registrationRepository = repo;
        registrationList.clear();
        registrationList = registrationRepository.loadAllRegistrations();
        //assign registration to officers and projects
        for(Registration reg: registrationList){
            reg.getRegisteredOfficer().addRegistration(reg);
            reg.getProject().getRegistrations().add(reg);
        }
        return true;
    }

    public static List<Registration> getAllRegitrations() {
        return registrationList;
    }

    public static void updateRegistrations(){
        registrationRepository.saveAllRegistrations(registrationList);
    }

    public static void addRegistration(Registration r) {
        registrationList.add(r);
     }
 
    public static void removeRegistration(Registration r) {
        registrationList.remove(r);
    }
}