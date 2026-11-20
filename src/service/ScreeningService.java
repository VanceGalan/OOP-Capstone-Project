package service;

import model.Movie;
import model.Screening;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * In-memory business service for managing movie screenings and approval workflow.
 */
public class ScreeningService {
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_APPROVED = "Approved";
    public static final String STATUS_REJECTED = "Rejected";

    private static final Comparator<Screening> BY_SHOW_TIME =
            Comparator.comparing(Screening::getShowDateTime, Comparator.nullsLast(Comparator.naturalOrder()));

    private final List<Screening> screenings = new ArrayList<>();

    public String generateNextScreeningId() {
        int nextNumber = 1;
        while (getScreeningById(String.format("SCR-%03d", nextNumber)) != null) {
            nextNumber++;
        }
        return String.format("SCR-%03d", nextNumber);
    }

    public void addScreening(Screening screening) {
        validateScreeningFields(screening, null);
        if (getScreeningById(screening.getScreeningId()) != null) {
            throw new IllegalArgumentException("Screening ID already exists.");
        }
        screening.setStatus(STATUS_PENDING);
        screening.setRejectionReason(null);
        screenings.add(screening);
    }

    /**
     * Seeds a screening directly with its stored status (e.g. Approved/Rejected demo data)
     * without forcing status back to Pending.
     */
    public void seedScreening(Screening screening) {
        if (screening == null || isBlank(screening.getScreeningId())) {
            throw new IllegalArgumentException("Screening and screening ID are required.");
        }
        if (getScreeningById(screening.getScreeningId()) != null) {
            throw new IllegalArgumentException("Screening ID already exists.");
        }
        screenings.add(screening);
    }

    public List<Screening> getAllScreenings() {
        List<Screening> copy = new ArrayList<>(screenings);
        copy.sort(BY_SHOW_TIME);
        return copy;
    }

    public Screening getScreeningById(String screeningId) {
        if (isBlank(screeningId)) {
            return null;
        }
        for (Screening screening : screenings) {
            if (Objects.equals(screening.getScreeningId(), screeningId)) {
                return screening;
            }
        }
        return null;
    }

    public List<Screening> getScreeningsByMovie(String movieId) {
        List<Screening> result = new ArrayList<>();
        if (isBlank(movieId)) {
            return result;
        }
        for (Screening screening : screenings) {
            if (Objects.equals(screening.getMovieId(), movieId)) {
                result.add(screening);
            }
        }
        result.sort(BY_SHOW_TIME);
        return result;
    }

    /**
     * Customer-facing showtime lookup: returns only Approved and future screenings for the movie.
     */
    public List<Screening> getScreeningsForMovie(Movie movie) {
        if (movie == null) {
            return new ArrayList<>();
        }
        return getScreeningsForMovie(movie.getMovieId());
    }

    public List<Screening> getScreeningsForMovie(String movieId) {
        List<Screening> result = new ArrayList<>();
        if (isBlank(movieId)) {
            return result;
        }
        LocalDateTime now = LocalDateTime.now();
        for (Screening screening : screenings) {
            if (Objects.equals(screening.getMovieId(), movieId) && isApprovedAndUpcoming(screening, now)) {
                result.add(screening);
            }
        }
        result.sort(BY_SHOW_TIME);
        return result;
    }

    public List<Screening> getApprovedScreenings() {
        List<Screening> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Screening screening : screenings) {
            if (isApprovedAndUpcoming(screening, now)) {
                result.add(screening);
            }
        }
        result.sort(BY_SHOW_TIME);
        return result;
    }

    public List<Screening> getPendingScreenings() {
        List<Screening> result = new ArrayList<>();
        for (Screening screening : screenings) {
            if (STATUS_PENDING.equalsIgnoreCase(screening.getStatus())) {
                result.add(screening);
            }
        }
        result.sort(BY_SHOW_TIME);
        return result;
    }

    public List<Screening> getScreeningsByOrganizer(String organizerId) {
        List<Screening> result = new ArrayList<>();
        if (isBlank(organizerId)) {
            return result;
        }
        for (Screening screening : screenings) {
            if (Objects.equals(screening.getOrganizerId(), organizerId)) {
                result.add(screening);
            }
        }
        result.sort(BY_SHOW_TIME);
        return result;
    }

    public int getPendingCount() {
        return getPendingScreenings().size();
    }

    public int getPendingCountByOrganizer(String organizerId) {
        if (isBlank(organizerId)) {
            return 0;
        }
        int count = 0;
        for (Screening screening : screenings) {
            if (Objects.equals(screening.getOrganizerId(), organizerId)
                    && STATUS_PENDING.equalsIgnoreCase(screening.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public List<Screening> getUpcomingScreeningsByMovie(String movieId) {
        List<Screening> result = new ArrayList<>();
        if (isBlank(movieId)) {
            return result;
        }
        LocalDateTime now = LocalDateTime.now();
        for (Screening screening : screenings) {
            if (Objects.equals(screening.getMovieId(), movieId)
                    && screening.getShowDateTime() != null
                    && screening.getShowDateTime().isAfter(now)
                    && !STATUS_REJECTED.equalsIgnoreCase(screening.getStatus())) {
                result.add(screening);
            }
        }
        result.sort(BY_SHOW_TIME);
        return result;
    }

    public boolean hasUpcomingScreenings(String movieId) {
        return !getUpcomingScreeningsByMovie(movieId).isEmpty();
    }

    /**
     * Updates a Pending or Rejected screening and resets it to Pending approval.
     * Approved screenings cannot be edited directly.
     */
    public void updateScreening(Screening updatedScreening) {
        if (updatedScreening == null || isBlank(updatedScreening.getScreeningId())) {
            throw new IllegalArgumentException("Screening ID is required.");
        }
        int existingIndex = findIndexById(updatedScreening.getScreeningId());
        if (existingIndex < 0) {
            throw new IllegalArgumentException("Screening not found.");
        }
        Screening existing = screenings.get(existingIndex);
        if (STATUS_APPROVED.equalsIgnoreCase(existing.getStatus())) {
            throw new IllegalStateException("Approved screenings cannot be edited.");
        }
        validateScreeningFields(updatedScreening, updatedScreening.getScreeningId());
        if (isBlank(updatedScreening.getOrganizerId())) {
            updatedScreening.setOrganizerId(existing.getOrganizerId());
        }
        updatedScreening.setStatus(STATUS_PENDING);
        updatedScreening.setRejectionReason(null);
        screenings.set(existingIndex, updatedScreening);
    }

    public boolean deleteScreening(String screeningId) {
        if (isBlank(screeningId)) {
            return false;
        }
        return screenings.removeIf(screening -> Objects.equals(screening.getScreeningId(), screeningId));
    }

    public int deleteScreeningsByMovie(String movieId) {
        if (isBlank(movieId)) {
            return 0;
        }
        int before = screenings.size();
        screenings.removeIf(screening -> Objects.equals(screening.getMovieId(), movieId));
        return before - screenings.size();
    }

    public boolean approveScreening(String screeningId) {
        Screening screening = getScreeningById(screeningId);
        if (screening == null) {
            return false;
        }
        screening.setStatus(STATUS_APPROVED);
        screening.setRejectionReason(null);
        return true;
    }

    public boolean rejectScreening(String screeningId, String reason) {
        Screening screening = getScreeningById(screeningId);
        if (screening == null) {
            return false;
        }
        screening.setStatus(STATUS_REJECTED);
        screening.setRejectionReason(isBlank(reason) ? "No reason provided." : reason.trim());
        return true;
    }

    private void validateScreeningFields(Screening screening, String excludeScreeningId) {
        if (screening == null) {
            throw new IllegalArgumentException("Screening cannot be null.");
        }
        if (isBlank(screening.getScreeningId())) {
            throw new IllegalArgumentException("Screening ID is required.");
        }
        if (isBlank(screening.getMovieId())) {
            throw new IllegalArgumentException("Please select a movie.");
        }
        if (isBlank(screening.getCinemaHall())) {
            throw new IllegalArgumentException("Please select a cinema hall.");
        }
        if (screening.getShowDateTime() == null || !screening.getShowDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Screening date and time must be in the future.");
        }
        if (screening.getPrice() <= 0) {
            throw new IllegalArgumentException("Ticket price must be greater than ₱0.");
        }
        if (hasHallTimeConflict(screening.getCinemaHall(), screening.getShowDateTime(), excludeScreeningId)) {
            throw new IllegalArgumentException("Another screening is already scheduled in "
                    + screening.getCinemaHall() + " at that exact date and time.");
        }
    }

    private boolean hasHallTimeConflict(String cinemaHall, LocalDateTime showDateTime, String excludeScreeningId) {
        for (Screening other : screenings) {
            if (Objects.equals(other.getScreeningId(), excludeScreeningId)) {
                continue;
            }
            if (STATUS_REJECTED.equalsIgnoreCase(other.getStatus())) {
                continue;
            }
            if (cinemaHall.equalsIgnoreCase(other.getCinemaHall())
                    && Objects.equals(other.getShowDateTime(), showDateTime)) {
                return true;
            }
        }
        return false;
    }

    private int findIndexById(String screeningId) {
        for (int i = 0; i < screenings.size(); i++) {
            if (Objects.equals(screenings.get(i).getScreeningId(), screeningId)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isApprovedAndUpcoming(Screening screening, LocalDateTime now) {
        return STATUS_APPROVED.equalsIgnoreCase(screening.getStatus())
                && screening.getShowDateTime() != null
                && screening.getShowDateTime().isAfter(now);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
