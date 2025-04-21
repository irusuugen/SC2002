package repository;

import entity.*;
import java.util.List;

public interface IUserRepository {
    List<User> loadAllUsers();
    void saveApplicant(List<User> users);
    void saveOfficer(List<User> users);
    void saveManager(List<User> users);
}