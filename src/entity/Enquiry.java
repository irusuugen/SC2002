/**
 * Represents an enquiry submitted by an applicant for a specific project.
 */

package entity;

public class Enquiry {
    private Applicant applicant;
    private String message;  //enquiry message from the applicant
    private Project project; //project the enquiry is related to
    private String answer;

    /**
     * Constructs an Enquiry with applicant, message and project specified.
     *
     * @param applicant           The applicant.
     * @param message             The message.
     * @param project             The BTO project.
     */
    public Enquiry(Applicant applicant, String message, Project project) 
    {
        this.applicant = applicant;
        this.message = message;
        this.project = project;
        this.answer = "(No reply yet)"; 
    }

    /**
     * Constructs an Enquiry with all attributes specified (used when loading from storage).
     *
     * @param applicant           The applicant.
     * @param message             The message.
     * @param project             The BTO project.
     * @param answer              The answer.
     */
    public Enquiry(Applicant applicant, String message, Project project, String answer) 
    {
        this.applicant = applicant;
        this.message = message;
        this.project = project;
        if (answer.equals("")) this.answer = "(No reply yet)";
        else this.answer = answer;
    }

    /** @return The applicant who asked the enquiry. */
    public Applicant getApplicant(){
        return applicant;
    }

    /** @return Message of the enquiry */
    public String getMessage() 
    {
        return message;
    }

    /** @return The project the enquiry belongs to */
    public Project getProject() 
    {
        return project;
    }

    /** @return The answer to the enquiry */
    public String getAnswer() 
    {
        return answer;
    }

    /** @param newMessage Sets the new message in enquiry */
    public void edit(String newMessage) 
    {
        this.message = newMessage;
    }

    /** @param answer Sets the answer to the enquiry */
    public void reply(String answer) 
    {
        this.answer = answer;
    }

}