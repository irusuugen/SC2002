import boundary.*;
import repository.UserRepository;
import repository.UserService;

public class Main {
    public static void main(String[] args) {
        UserService.startUserStorage(new UserRepository());

        Welcome welcome = new Welcome();
        while (true) {
            welcome.welcome();
        }
        
    }
}
