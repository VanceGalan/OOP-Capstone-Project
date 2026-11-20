package model;

import java.util.Date;

public class Booking {
    private String bookingId;
    private String customerId;
    private String screeningId;
    private Date bookingDate;
    private String status;

    public Booking(String bookingId, String customerId, String screeningId, Date bookingDate, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.screeningId = screeningId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public double calculateTotal() {

        return 0;
    }

    public void confirmBooking() {

    }

    public void cancelBooking() {

    }
}
