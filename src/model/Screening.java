package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Screening implements Manageable{
    private String screeningId;
    private String movieId;
    private LocalDateTime showDateTime; // Format: Sat, Nov 14, 2026 • 7:00 PM
    private String cinemaHall;
    private double price; // ONE price per screening — NO VIP
    private String status; // Pending / Approved / Rejected
    private String organizerId;
    private String rejectionReason;


    public Screening(String screeningId, String movieId, LocalDateTime showDateTime, double price, String status) {
        this(screeningId, movieId, showDateTime, null, price, status, null, null);
    }
    public Screening(String screeningId, String movieId, LocalDateTime showDateTime,String cinemaHall, double price, String status) {
        this(screeningId, movieId, showDateTime, cinemaHall, price, status, null, null);
    }
    public Screening(String screeningId, String movieId, LocalDateTime showDateTime, String cinemaHall, double price, String status, String organizerId) {
        this(screeningId, movieId, showDateTime, cinemaHall, price, status, organizerId, null);
    }
    public Screening(String screeningId, String movieId, LocalDateTime showDateTime, String cinemaHall, double price, String status, String organizerId, String rejectionReason) {
        this.screeningId = screeningId;
        this.movieId = movieId;
        this.showDateTime = showDateTime;
        this.cinemaHall = cinemaHall;
        this.price = price;
        this.status = status;
        this.organizerId = organizerId;
        this.rejectionReason = rejectionReason;
    }
    public String getFormattedDateTime() {
        if (showDateTime == null) {
            return "";
        }
        return showDateTime.format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy • h:mm a"));
    }

    public LocalDateTime getShowDateTime() { return showDateTime; }

    public void setShowDateTime(LocalDateTime showDateTime) {this.showDateTime = showDateTime; }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(String cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
