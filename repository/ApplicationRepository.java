package repository;

import entity.Application;
import java.util.List;

public interface ApplicationRepository {
    List<Application> loadAllApplications();
    void saveAllApplications(List<Application> applications);
}