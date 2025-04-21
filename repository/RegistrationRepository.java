package repository;

import entity.*;
import java.util.*;

public interface RegistrationRepository {
    List<Registration> loadAllRegistrations();
    void saveAllRegistrations(List<Registration> registrations);
}
