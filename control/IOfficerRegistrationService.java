package control;

import entity.HDBOfficer;

public interface IOfficerRegistrationService {
    void registerForProject(HDBOfficer officer);
    void viewRegistrations(HDBOfficer officer);
    void printAssignedProject(HDBOfficer officer);
}
