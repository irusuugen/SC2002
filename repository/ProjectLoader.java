package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import entity.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectLoader {
    public ArrayList<Project> readProjects(String filename) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            br.readLine(); // Ignore headings
            while ((currentLine = br.readLine()) != null) {
                String[] projectInfo = currentLine.split(",");
                String projectName = projectInfo[0];
                String neighborhood = projectInfo[1];
                int num2Room = Integer.valueOf(projectInfo[3]);
                int num2SellingPrice = Integer.valueOf(projectInfo[4]);
                int num3Room = Integer.valueOf(projectInfo[6]);
                int num3SellingPrice = Integer.valueOf(projectInfo[7]);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate applicationOpenDate = LocalDate.parse(projectInfo[8], dateFormatter);
                LocalDate applicationCloseDate = LocalDate.parse(projectInfo[9], dateFormatter);
                HDBManager projectManager = UserService.getManagers().stream()
                    .filter(m -> m.getName().equals(projectInfo[10]))
                    .findFirst()
                    .orElse(null);
                int officerSlots = Integer.valueOf(projectInfo[11]);
                String[] officerNames = projectInfo[12].replace("\"", "").split(",");
                List<HDBOfficer> officerSlotList = UserService.getOfficers().stream()
                    .filter(o->Arrays.asList(officerNames).contains(o.getName()))
                    .collect(Collectors.toList());;
                
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
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return projects;
    }
}