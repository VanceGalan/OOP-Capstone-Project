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

    public Screening(String screeningId, String movieId, LocalDateTime showDateTime, double price, String status) {
        this.screeningId = screeningId;
        this.movieId = movieId;
        this.showDateTime = showDateTime;
        this.price = price;
        this.status = status;
    }
    public String getFormattedDateTime() {
        return showDateTime.format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy • h:mm a"));
    }

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
