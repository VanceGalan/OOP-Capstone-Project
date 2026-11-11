package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Screening {
    private String screeningId;
    private String movieId;
    private LocalDateTime showDateTime; // Format: Sat, Nov 14, 2026 • 7:00 PM
    private String cinemaHall;
    private double price; // ONE price per screening — NO VIP
    private String status; // Pending / Approved / Rejected
    public String getFormattedDateTime() {
        return showDateTime.format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy • h:mm a"));
    }
}
