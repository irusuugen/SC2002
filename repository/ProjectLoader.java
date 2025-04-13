package repository;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import entity.*;

public class ProjectLoader {
    public ArrayList<Project> readProjects(String filename, List<HDBManager> managerList, List<HDBOfficer> officerList) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            br.readLine(); // Ignore headings
            while ((currentLine = br.readLine()) != null) {
                // Obtaining basic information
                String[] projectInfo = currentLine.split(",");
                String projectName = projectInfo[0];
                String neighborhood = projectInfo[1];
                int num2Room = Integer.valueOf(projectInfo[3]);
                int num2SellingPrice = Integer.valueOf(projectInfo[4]);
                int num3Room = Integer.valueOf(projectInfo[6]);
                int num3SellingPrice = Integer.valueOf(projectInfo[7]);
                
                // This section is to be updated with the correct code!!
                Date applicationOpenDate = projectInfo[8];
                Date applicationCloseDate = projectInfo[9];
                
                HDBManager projectManager = managerList.stream()
                    .filter(m -> m.getName().equals(projectInfo[10]))
                    .findFirst()
                    .orElse(null);
                int officerSlots = Integer.valueOf(projectInfo[11]);
                List<HDBOfficer> = officerList.stream()
                    .filter(o -> o.getName().equals())
                String[] officerNameList = projectInfo[12].split(",");

            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return projects;
    }
}