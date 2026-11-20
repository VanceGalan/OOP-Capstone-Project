package model;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String bookingId;
    private String qrCode;
    private String cinemaHall;
    private Date issuedAt;

    public Ticket(String ticketId, String bookingId, String qrCode, String cinemaHall, Date issuedAt) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.qrCode = qrCode;
        this.cinemaHall = cinemaHall;
        this.issuedAt = issuedAt;
    }

    public String generateQRCode() {

        return null;
    }

    public boolean validateTicket() {

        return false;
    }

    public void saveTicketToFile() {

    }
}
