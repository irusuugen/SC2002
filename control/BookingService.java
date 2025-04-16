package control;

import entity.*;

public class BookingService {
    public static void createBooking(Application application) {
        if (application.getStatus() == Status.SUCCESSFUL) {
            application.setBookingRequested(true);
            System.out.println("Booking request has been sent to the HDB Officers.");
        } else {
            System.out.println("Booking not available right now. Please check your application status.");
        }
    }
}
