package control;

import entity.*;
import java.util.*;
import utils.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HDBManagerProjectController {
    private Scanner sc = new Scanner(System.in);

    public void createProject(HDBManager manager, List<Project> allProjects) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

        System.out.print("Enter project name: ");
        String name = sc.nextLine();
        System.out.print("Enter neighborhood: ");
        String neighborhood = sc.nextLine();
        System.out.print("Enter 2-room units: ");
        int twoRooms = sc.nextInt();
        System.out.print("Enter 3-room units: ");
        int threeRooms = sc.nextInt();
        System.out.print("Enter 2-room price: ");
        int twoRoomsPrice = sc.nextInt();
        System.out.print("Enter 3-room price: ");
        int threeRoomsPrice = sc.nextInt();
        System.out.print("Enter officer slots: ");
        int officerSlots = sc.nextInt();
        System.out.print("Enter opening date (DD/MM/YYYY): ");
        LocalDate openDate = LocalDate.parse(sc.next(), formatter);
        System.out.print("Enter closing date (DD/MM/YYYY): ");
        LocalDate closeDate = LocalDate.parse(sc.next(), formatter);
        sc.nextLine();
        Project p = new Project(name, neighborhood, twoRooms, twoRoomsPrice, threeRooms, threeRoomsPrice, openDate, closeDate, manager, officerSlots, new ArrayList<>());
        manager.getCreatedProjects().add(p);
        allProjects.add(p);
        System.out.println("Project created.");
    }

    public void toggleProjectVisibility(HDBManager manager) {
        List<Project> projects = manager.getCreatedProjects();
        for (int i = 0; i < projects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, projects.get(i).getProjectName());
        }
        int idx = IntGetter.readInt("Select project to toggle visibility: ") - 1;
        if (idx >= 0 && idx < projects.size()) {
            Project p = projects.get(idx);
            p.setVisibility(!p.isVisible());
            System.out.println("Visibility toggled.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
}
