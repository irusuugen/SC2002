package boundary;

import entity.*;
import utils.*;

import java.util.List;
import java.util.Scanner;

public class HDBOfficerMenu {
	public class ApplicantMenu {
	    public static void applicantMenu(User user) {
	        Scanner sc = new Scanner(System.in);
	        
	        while (true) {  // Main menu loop
	            ClearPage.clearPage();
	            System.out.println("""
	            ╔════════════════════════════════════════════╗
	            ║               Officer Menu                 ║
	            ╠════════════════════════════════════════════╣
	            ║  1. Change password                        ║
	            ║  2. View all open projects                 ║
	            ║  3. Apply for a project                    ║
	            ║  4. View application                       ║
	            ║  5. Book flat                              ║
	            ║  6. Request withdrawal for application     ║
	            ║  7. Submit enquiry about a project         ║
	            ║  8. View enquiries                         ║
	            ║  9. Edit enquiries                         ║
	            ║  10. Delete enquiries                      ║
	            ║  11. Register to join a project            ║
	            ║  12. View registration                     ║
	            ║  13. View handling project                 ║
	            ║  14. View enquiries on handling project    ║
	            ║  15. Reply enquiries on handling project   ║
	            ║  16. Update successful application         ║
	            ║  17. Logout                                ║
	            ╚════════════════════════════════════════════╝
	            """);
	            
	            System.out.print("Enter your choice: ");
	            
	            int choice = sc.nextInt();
	            sc.nextLine();  // Consume the newline character
	            List<Enquiry> listEnquiry;
	            switch (choice) {
	                case 1:
	                    // Change password functionality
	                    System.out.println("Change password selected");
	                    break;
	                case 2:
	                    // View projects functionality
	                    ApplicantController.viewOpenProjects(applicant);
	                    System.out.println("Press enter to go back");
	                    sc.nextLine();
	                    break;
	                case 3:
	                    // Apply for project
	                    break;
	                case 4:
	                    // View application
	                    break;
	                case 5:
	                    // Book with officer
	                    break;
	                case 6:
	                    // Request withdrawal
	                    break;
	                case 7:
	                    // Submit enquiry
	                    break;
	                case 8:
	                    // View enquiry
	                    break;
	                case 9:
	                    // Edit enquiry
	                    break;
	                case 10:
	                    // Delete enquiry
	                    break;
	                case 11:
	                	// print all projects that able to register
	                	System.out.println("Please enter the number of the project that you want to register for:");
	                    int project_choice = sc.nextInt();
	                    sc.nextLine();
	                    
	                    user.registerForProject(project);
	                    System.out.println("Registered");
	                    break;
	                case 12:
	                	List<Registration> registrationList = user.getRegistrationList();
	                	// print registrationList
	                    break;
	                case 13:
	                	Project project=user.getAssignedProject();
	                	// print project
	                    break;
	                case 14:
	                	listEnquiry = user.getAssignedProject().getEnquiries();
	                	// print listEnquiry
	                    break;
	                case 15:
	                	listEnquiry = user.getAssignedProject().getEnquiries();
	                	// print listEnquiry
	                	System.out.println("Please enter the number of the enquiry that you want to reply: ");
	                    int enquiry_choice = sc.nextInt();
	                    sc.nextLine();
	                    System.out.println("Enter your response: ");
	                    String response = sc.nextLine();
	                    listEnquiry[enquiry_choice].reply(response);
	                    System.out.println("replied");
	                    break;
	                case 16:
	                	System.out.println("Please enter applicant’s NRIC: ");
	                    String nric = sc.nextLine();
	                    // search for application by nric
	                    HDBOfficerApplicationController.updateApplication(application);
	                	// generate receipt
	                    break;
	                case 17:
	                    System.out.println("Logging out...");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please enter a number from 1-17.");
	            }
	        }
	    }
	}
}
