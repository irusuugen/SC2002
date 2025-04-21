import boundary.*;
import repository.*;
import entity.Project;
import entity.FlatType;
import entity.Filter;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService.startUserStorage(new UserCsvRepository());
        ProjectService.startProjectStorage(new ProjectCsvRepository());
        ApplicationService.startApplicationStorage(new ApplicationCsvRepository());
        EnquiryService.startEnquiryStorage(new EnquiryCsvRepository());
        RegistrationService.startRegistrationStorage(new RegistrationCsvRepository());

        Welcome welcome = new Welcome();

        while (true) {
            welcome.welcome();
        }
    }
}
