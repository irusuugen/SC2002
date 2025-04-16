import boundary.*;
import repository.ProjectRepository;
import repository.ProjectService;
import repository.UserRepository;
import repository.UserService;

public class Main {
    public static void main(String[] args) {
        UserService.startUserStorage(new UserRepository());
        ProjectService.startProjectStorage(new ProjectRepository());
        ProjectViewer.printProjects(ProjectService.getAllProjects());
        // Welcome welcome = new Welcome();
        // while (true) {
        //     welcome.welcome();
        // }
        
    }
}
