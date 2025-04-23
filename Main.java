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

        while (true) {
            welcome.welcome();
        }
    }
}
