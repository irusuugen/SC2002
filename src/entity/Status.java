/**
 * Enum representing the different statuses in the system.
 * The statuses are used for both applications and registrations.
 */

package entity;

public enum Status 
{
        PENDING, // both
        UNSUCCESSFUL, SUCCESSFUL, BOOKED, // for applications
        APPROVED, REJECTED; // for registrations
}