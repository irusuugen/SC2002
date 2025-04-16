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
			    if(user.getAssignedProject()!=null)
			    {
				System.out.println("You are a HDB Officer for another project.");
				break;
			    }
			    List<Project> registrableProjects = new ArrayList<>();
			    for (Project project : ProjectService.getAllProjects())
			    {
				if (project.getVisibility() && project!=user.getAssignedProject() && project!=user.getApplication().getProject())
				{
					registrableProjects.add(project);
				}
	                    }
	                    System.out.print("Please enter the project name that you want to register for:");
	                    String projectName = sc.nextLine();
	                    for (Project project : registrableProjects)
	                    {
	                        if(project.getProjectName()==projectName)
	                        {
	                            HDBOfficerRegistrationController.registerForProject(user,project);
	    	                    System.out.println("Registered");
	    	                    break;
	                        }
	                    }
	                    break;
	                case 12:
	                    List<Registration> registrationList = user.getRegistrationList();
	                    // print registrationList
	                    break;
	                case 13:
	                    user.getAssignedProject().printProjectDetails();
	                    break;
	                case 14:
	                    List<Enquiry> listEnquiries = user.getAssignedProject().getEnquiries();
	                    for (Enquiry enquiry : user.getAssignedProject().getEnquiries())
	                    {
	                	System.out.printf("Message: %s ,Reply: ",enquiry.getMessage());
	                	if (enquiry.getAnswer()!=null) 
	                	{
				    System.out.println(enquiry.getAnswer());
	                        }
				else 
	                        {
	                            System.out.println("(No reply yet)");
	                        }
	                    }
	                    break;
	                case 15:
			    List<Enquiry> listNoReplyEnquiries = new ArrayList<>();
	                    for (Enquiry enquiry : user.getAssignedProject().getEnquiries())
	                    {
	                	if (enquiry.getAnswer()==null) listNoReplyEnquiries.add(enquiry);
	                    }
	                    for (int i=0;i<listNoReplyEnquiries.size();i++)
	                    {
	                	System.out.printf("%d. Message: %s\n",i+1,listNoReplyEnquiries[i].getMessage());
	                    }
	                    System.out.print("Please enter the number of the enquiry that you want to reply: ");
	                    int enquiry_choice = sc.nextInt();
	                    sc.nextLine();
	                    System.out.print("Enter your response: ");
	                    String response = sc.nextLine();
	                    listNoReplyEnquiries[enquiry_choice-1].reply(response);
	                    System.out.println("replied");
	                    break;
	                case 16:
	                    System.out.print("Please enter applicant’s NRIC: ");
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
