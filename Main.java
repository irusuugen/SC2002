import boundary.*;
import repository.*;
import entity.Project;
import entity.FlatType;
import entity.Filter;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService.startUserStorage(new UserRepository());
        ProjectService.startProjectStorage(new ProjectRepository());
        ApplicationService.startApplicationStorage(new ApplicationRepository());
        EnquiryService.startEnquiryStorage(new EnquiryRepository());
        RegistrationService.startRegistrationStorage(new RegistrationRepository());

        Welcome welcome = new Welcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            welcome.welcome();
//            System.out.println("Please select an option:");
//            System.out.println("1. Filter projects");
//            System.out.println("2. Exit");
//
//            int choice = scanner.nextInt();
//            if (choice == 1) {
//                // Handle filtering
//                filterProjects();
//            } else if (choice == 2) {
//                break; // Exit the program
//            } else {
//                System.out.println("Invalid choice, please try again.");
//            }
        }
    }

    // Function to filter projects based on user input
//    public static void filterProjects() {
//        Scanner scanner = new Scanner(System.in);
//
//        List<Project> projects = ProjectService.getAllProjects();
//
//        System.out.println("Select filter criteria:");
//        System.out.println("1. Filter by 2-room flats available");
//        System.out.println("2. Filter by 3-room flats available");
//        System.out.println("3. Filter by neighborhood");
//        System.out.println("4. Exit");
//
//        int filterChoice = scanner.nextInt();
//        List<Project> filteredProjects = null;
//
//        if (filterChoice == 1 || filterChoice == 2) {
//            System.out.println("Enter minimum number of available flats:");
//            int minAvailable = scanner.nextInt();
//            filteredProjects = Filter.filterProjects(projects, filterChoice, minAvailable, "", FlatType.TWOROOMS);
//        } else if (filterChoice == 3) {
//            System.out.println("Enter neighborhood:");
//            String neighborhood = scanner.next();
//            filteredProjects = Filter.filterProjects(projects, filterChoice, 0, neighborhood, FlatType.TWOROOMS);
//        } else if (filterChoice == 4) {
//            return; // Exit filter
//        } else {
//            System.out.println("Invalid choice, please try again.");
//            return;
//        }
//
//
//        if (filteredProjects != null && !filteredProjects.isEmpty()) {
//            System.out.println("Filtered Projects:");
//            for (Project project : filteredProjects) {
//                project.printProjectDetails();
//            }
//        } else {
//            System.out.println("No projects found based on the given filter.");
//        }
//    }
}
