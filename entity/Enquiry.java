package entity;

public class Enquiry {
    private Applicant applicant;
    private String message;  //enquiry message from the applicant
    private Project project; //project the enquiry is related to
    private String answer;

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
        if (answer.equals("")) this.answer = "(No reply yet)";
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

    public void edit(String newMessage) 
    {
        this.message = newMessage;
    }

    public void reply(String answer) 
    {
        this.answer = answer;
    }

}