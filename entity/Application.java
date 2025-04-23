package entity;

import java.time.LocalDate;

public class Application {

    private Project project;
    private FlatType flatType;
    private Applicant applicant;
    private LocalDate date;
    private Status status;
    private boolean bookingRequested;
    private boolean withdrawalRequested;

    public Application(Project project, FlatType flatType, Applicant applicant) 
    {
        this.project = project;
        this.flatType = flatType;
        this.applicant = applicant;
        this.date = LocalDate.now();
        this.status = Status.PENDING;
        this.bookingRequested = false;
        this.withdrawalRequested = false;
    }

    public Application(Project project, FlatType flatType, Applicant applicant, Status status, LocalDate date, boolean bookingRequested, boolean withdrawalRequested) 
    {
        this.project = project;
        this.flatType = flatType;
        this.applicant = applicant;
        this.date = date;
        this.status = status;
        this.bookingRequested = bookingRequested;
        this.withdrawalRequested = withdrawalRequested;
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
            status = Status.UNSUCCESSFUL;
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

    public boolean isBookingRequested() {
        return bookingRequested;
    }

    public void setBookingRequested(boolean bookingRequested) {
        this.bookingRequested = bookingRequested;
    }

    public boolean isWithdrawalRequested() {
        return withdrawalRequested;
    }

    public void setWithdrawalRequested(boolean withdrawalRequested) {
        this.withdrawalRequested = withdrawalRequested;
    }

}