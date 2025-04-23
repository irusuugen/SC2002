/**
 * This class is the entry point of the BTO Management System.
 * It initializes all major services and loads data from the CSV files (users, projects, enquiries, applications, etc.)
 * using their respective repository implementations. Once initialization is complete,
 * it launches the Welcome interface, allowing users to log in and interact with the system.
 *
 */

import boundary.*;
import repository.*;

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
