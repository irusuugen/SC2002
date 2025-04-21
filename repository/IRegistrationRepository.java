package repository;

import entity.*;
import java.util.*;

public interface IRegistrationRepository {
    List<Registration> loadAllRegistrations();
    void saveAllRegistrations(List<Registration> registrations);
}
