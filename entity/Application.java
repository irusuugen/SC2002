/**
 * Represents an application submitted by an applicant for a flat under a specific BTO project.
 * Each application tracks the applicant, project, flat type, application date, and status.
 * It also records whether a booking or withdrawal has been requested.
 */

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

    /**
     * Constructs a new Application with default status as PENDING and today's date.
     *
     * @param project   The BTO project being applied to.
     * @param flatType  The type of flat applied for (e.g., 2-Room, 3-Room).
     * @param applicant The applicant submitting the application.
     */
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

    /**
     * Constructs an Application with all attributes specified (used when loading from storage).
     *
     * @param project             The BTO project.
     * @param flatType            The flat type applied for.
     * @param applicant           The applicant.
     * @param status              The application status.
     * @param date                The date of application.
     * @param bookingRequested    Whether the applicant has requested a booking.
     * @param withdrawalRequested Whether the applicant has requested a withdrawal.
     */
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

    /** @return The project this application belongs to. */
    public Project getProject() 
    {
        return project;
    }

    /** @return The flat type applied for. */
    public FlatType getFlatType() 
    {
        return flatType;
    }

    /** @return The applicant who submitted the application. */
    public Applicant getApplicant() 
    {
        return applicant;
    }

    /** @return The date when the application was made. */
    public LocalDate getDate() 
    {
        return date;
    }

    /** @return The current status of the application. */
    public Status getStatus() 
    {
        return status;
    }

    /** @return The application status as a string. */
    public String getStatusString() 
    {
        return status.toString();
    }

    /**
     * Withdraws the application if its status is PENDING, SUCCESSFUL, or BOOKED,
     * by marking it as UNSUCCESSFUL.
     */
    public void withdraw()
    {
        if (status == Status.PENDING || status == Status.SUCCESSFUL || status == Status.BOOKED) {
            status = Status.UNSUCCESSFUL;
        }
    }

    /**
     * Sets the status of the application using a string (case-insensitive).
     *
     * @param status The new status as a string (e.g., "successful").
     */
    public void setStatus(String status) 
    {
        this.status = Status.valueOf(status.toUpperCase());
    }

    /**
     * Marks the application as UNSUCCESSFUL if it is currently PENDING.
     */
    public void markUnsuccessful() 
    {
        if (status == Status.PENDING) {
            status = Status.UNSUCCESSFUL;
        }
    }

    /**
     * Marks the application as SUCCESSFUL if it is currently PENDING.
     */
    public void markSuccessful() 
    {
        if (status == Status.PENDING) {
            status = Status.SUCCESSFUL;
        }
    }

    /**
     * Marks the application as BOOKED if it is currently SUCCESSFUL.
     */
    public void markBooked() 
    {
        if (status == Status.SUCCESSFUL) {
            status = Status.BOOKED;
        }
    }

    /** @return True if booking has been requested. */
    public boolean isBookingRequested() {
        return bookingRequested;
    }

    /** @param bookingRequested Sets whether booking has been requested. */
    public void setBookingRequested(boolean bookingRequested) {
        this.bookingRequested = bookingRequested;
    }

    /** @return True if withdrawal has been requested. */
    public boolean isWithdrawalRequested() {
        return withdrawalRequested;
    }

    /** @param withdrawalRequested Sets whether withdrawal has been requested. */
    public void setWithdrawalRequested(boolean withdrawalRequested) {
        this.withdrawalRequested = withdrawalRequested;
    }

}