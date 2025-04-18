package entity;

public class Enquiry {
    private Applicant applicant;
    private String message;  //enquiry message from the applicant
    private Project project; //project the enquiry is related to
    private String answer;   //reply to the enquiry (can be null if no reply yet)

    public Enquiry(Applicant applicant, String message, Project project) 
    {
        this.applicant = applicant;
        this.message = message;
        this.project = project;
        this.answer = "(No reply yet)"; 
    }

    public Enquiry(Applicant applicant, String message, Project project, String answer) 
    {
        this.applicant = applicant;
        this.message = message;
        this.project = project;
        if(answer.equals("")) this.answer = "(No reply yet)";
        else this.answer = answer;
    }

    public Applicant getApplicant(){
        return applicant;
    }

    public String getMessage() 
    {
        return message;
    }

    public Project getProject() 
    {
        return project;
    }

    public String getAnswer() 
    {
        return answer;
    }

    // //view the details of the enquiry, including the message and any reply
    // public void view() 
    // {
    //     System.out.println("Enquiry about Project: " + project.getProjectName());
    //     System.out.println("Message: " + message);
    //     if (answer!=null) 
    //     {
    //         System.out.println("Reply: " + answer);
    //     } else 
    //     {
    //         System.out.println("Reply: (No reply yet)");
    //     }
    // }

    public void edit(String newMessage) 
    {
        this.message = newMessage;
    }

    public void reply(String answer) 
    {
        this.answer = answer;
    }
   
    // @Override
    // public String toString() {
    //     return "Project: " + project.getProjectName() +
    //            "\nMessage: " + message +
    //            "\nAnswer: " + (answer != null ? answer : "(No reply yet)");
    // }
}

/*
Using view()
enquiry.view();  // Will print: "Enquiry about Project: Yishun BTO Project", "Message: What is the application deadline?", etc.

Using toString() implicitly with System.out.println
System.out.println(enquiry);  // Will print: "Project: Yishun BTO Project\nMessage: What is the application deadline?\nAnswer: (No reply yet)"
*/ 
