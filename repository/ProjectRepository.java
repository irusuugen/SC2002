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

import entity.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectRepository {

    /**
     * Loads all projects available in the system from the default project list file.
     *
     * @return A list of all Project objects loaded from "data/ProjectList.csv".
     */
    public List<Project> loadAllProjects() {
        List<Project> allProjects = new ArrayList<>(readProjects("data/ProjectList.csv"));
        return allProjects;
    }

    /**
     * Reads project data from the specified CSV file and constructs a list of {@link Project} objects.
     *
     * This method parses project details (project name, neighborhood, room types, etc.),
     * and associates them with the correct manager and officers based on NRICs
     *
     * @param filename The path to the CSV file containing project data.
     * @return A list of Project instances parsed from the file.
     */
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
                    .filter(m -> m.getNric().equals(projectInfo[10]))
                    .findFirst()
                    .orElse(null);

                int officerSlots = Integer.parseInt(projectInfo[11]);
                String[] officerNric = projectInfo[12].replace("\"", "").split(",");
                List<HDBOfficer> officerSlotList = UserService.getOfficers().stream()
                    .filter(o->Arrays.asList(officerNric).contains(o.getNric()))
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

    /**
     * Writes the provided list of projects to a CSV file.
     *
     * Each project's key attributes, including flat availability and assigned officers,
     * are formatted and written in CSV structure suitable for reloading.
     *
     * @param filename The destination file path to write the project list.
     * @param projects The list of Project instances to be serialized.
     */
    public static void writeToProjectList(String filename, List<Project> projects){
        List<String> rows = new ArrayList<>();
        String header = "Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer";
        rows.add(header);
        for(Project p: projects){
            List<String> row = new ArrayList<>();
            row.add(p.getProjectName());
            row.add(p.getNeighborhood());
            row.add("2-Room");
            row.add(String.valueOf(p.getNumFlatAvailable(FlatType.TWOROOMS)));
            row.add(String.valueOf(p.getSellingPrice(FlatType.TWOROOMS)));
            row.add("3-Room");
            row.add(String.valueOf(p.getNumFlatAvailable(FlatType.THREEROOMS)));
            row.add(String.valueOf(p.getSellingPrice(FlatType.THREEROOMS)));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yy");
            row.add(p.getOpenDate().format(dateFormatter));
            row.add(p.getOpenDate().format(dateFormatter));
            row.add(p.getManager().getNric());
            row.add(String.valueOf(p.getOfficerSlot()));
            List<String> officers = new ArrayList<>();
            for(HDBOfficer o: p.getOfficerSlotList()){
                officers.add(o.getNric());
            }
            row.add("\""+String.join(",",officers)+"\"");
            rows.add(String.join(",", row));
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String row : rows) {
                writer.println(row);
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}