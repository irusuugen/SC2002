package entity;

import java.time.LocalDate;

public class Application {

    private Project project;
    private FlatType flatType;
    private Applicant applicant;
    private LocalDate date;
    private Status status;

    public Application(Project project, FlatType flatType, Applicant applicant) 
    {
        this.project = project;
        this.flatType = flatType;
        this.applicant = applicant;
        this.date = LocalDate.now();
        this.status = Status.PENDING;
    }

    public Project getProject() 
    {
        return project;
    }

    public FlatType getFlatType() 
    {
        return flatType;
    }

    public Applicant getApplicant() 
    {
        return applicant;
    }

    public LocalDate getDate() 
    {
        return date;
    }

    public Status getStatus() 
    {
        return status;
    }

    //converts the status enum to a string with " "
    public String getStatusString() 
    {
        return status.toString();
    }

    //change the status to withdraw if its only pending or successful only
    public void withdraw() 
    {
        if (status == Status.PENDING || status == Status.SUCCESSFUL) {
            status = Status.WITHDRAWN;
        }
    }

    //change the string status to match enum uppercase
    public void setStatus(String status) 
    {
        this.status = Status.valueOf(status.toUpperCase());
    }

    //mark unsuccessful only if pending
    public void markUnsuccessful() 
    {
        if (status == Status.PENDING) {
            status = Status.UNSUCCESSFUL;
        }
    }

    //opp to prev func
    public void markSuccessful() 
    {
        if (status == Status.PENDING) {
            status = Status.SUCCESSFUL;
        }
    }

    //mark the status as booked only if successful
    public void markBooked() 
    {
        if (status == Status.SUCCESSFUL) {
            status = Status.BOOKED;
        }
    }

    //check if application is still ongoing
    public boolean isActive() 
    {
        return status == Status.PENDING || status == Status.SUCCESSFUL;
    }

    // i used NRIC instead of name --> cuz unique
    // @Override
    // public String toString() 
    // {
    //     String nric = applicant.getNric();
    //     String maskedNric = "****" + nric.substring(nric.length() - 4);
    //     return "Applicant: " + maskedNric +
    //            ", Project: " + project.getProjectName() +
    //            ", Flat Type: " + flatType +
    //            ", Date: " + date +
    //            ", Status: " + status;
    // }
}