/* Class is for storage and access to list of all application in the database */

package repository;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    private static RegistrationRepository registrationRepository;
    private static List<Registration> registrationList = new ArrayList<>();

    public static boolean startRegistrationStorage(RegistrationRepository repo) {
        registrationRepository = repo;
        registrationList.clear();
        registrationList = registrationRepository.loadAllRegistrations();
        //assign application to applicants and projects
        for(Registration reg: registrationList){
            reg.getRegisteredOfficer().getRegistrationList().add(reg);
            reg.getProject().getRegistrations().add(reg);
        }
        return true;
    }

    public static List<Registration> getAllRegitrations() {
        return registrationList;
    }

    public static void updateRegistrations(){
        RegistrationRepository.writeToRegistrationList("data/RegistrationList.csv", registrationList);
    }
}