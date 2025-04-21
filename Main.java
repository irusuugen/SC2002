/**
 * This class is the entry point of the BTO Management System.
 * It initializes all major services and loads data from the CSV files (users, projects, enquiries, applications, etc.)
 * using their respective repository implementations. Once initialization is complete,
 * it launches the Welcome interface, allowing users to log in and interact with the system.
 *
 * @author Michelle Aye
 * @version 1.0
 * @since 2025-04-21
 */

import boundary.*;
import repository.*;

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
