package control;

import entity.HDBManager;
import entity.Registration;
import entity.Status;
import java.util.List;

public interface IManagerRegistrationService {
    void viewRegistrations(HDBManager manager);
    void processRegistrations(HDBManager manager);
}
