/**
 * Handles reading and writing of information regarding the BTO projects
 * from and to a CSV file.
 * This repository is responsible for parsing project information from storage
 * and converting it into Project objects. It also serializes a list of
 * projects back into the appropriate CSV format.
 *
 *
 * @author Michelle Aye,
 * @author Trongmetheerat Theeraphat
 * @version 1.0
 * @since 2025-04-21
 */

package repository;

import entity.Project;
import java.util.List;

public interface IProjectRepository {
    List<Project> loadAllProjects();
    void saveAllProjects(List<Project> projects);
}