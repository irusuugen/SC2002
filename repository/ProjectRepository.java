/* Class is for reading ProjectList csv file */

package repository;

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectRepository {
    public List<Project> loadAllProjects() {
        List<Project> allProjects = new ArrayList<>();
        allProjects.addAll(readProjects("data/ProjectList.csv"));
        return allProjects;
    }

    public List<Project> readProjects(String filename) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            br.readLine(); // Ignore headings
            while ((currentLine = br.readLine()) != null) {
                // Obtaining the basic information
                String[] projectInfo = currentLine.split(",");
                String projectName = projectInfo[0];
                String neighborhood = projectInfo[1];
                int num2Room = Integer.parseInt(projectInfo[3]);
                float num2SellingPrice = Float.parseFloat(projectInfo[4]);
                int num3Room = Integer.parseInt(projectInfo[6]);
                float num3SellingPrice = Float.parseFloat(projectInfo[7]);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate applicationOpenDate = LocalDate.parse(projectInfo[8], dateFormatter);
                LocalDate applicationCloseDate = LocalDate.parse(projectInfo[9], dateFormatter);
                HDBManager projectManager = UserService.getManagers().stream()
                    .filter(m -> m.getName().equals(projectInfo[10]))
                    .findFirst()
                    .orElse(null);
                int officerSlots = Integer.parseInt(projectInfo[11]);
                String[] officerNames = projectInfo[12].replace("\"", "").split(",");
                List<HDBOfficer> officerSlotList = UserService.getOfficers().stream()
                    .filter(o->Arrays.asList(officerNames).contains(o.getName()))
                    .collect(Collectors.toList());;

                // Creates a project and adds to projectList
                Project project = new Project(
                    projectName,
                    neighborhood,
                    num2Room,
                    num2SellingPrice,
                    num3Room,
                    num3SellingPrice,
                    applicationOpenDate,
                    applicationCloseDate,
                    projectManager,
                    officerSlots,
                    officerSlotList
                );
                projects.add(project);
                if (projectManager != null) {
                    projectManager.addCreatedProject(project);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return projects;
    }
}