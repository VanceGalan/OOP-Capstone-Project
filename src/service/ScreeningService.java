package service;

import model.Screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ScreeningService {
    private final List<Screening> screenings = new ArrayList<>();

    public void  addScreening(Screening screening) {
        if(screening == null) {
            throw new IllegalArgumentException("Screening cannot be null.");
        }
        if(!screening.getShowDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Screening must be in the future.");
        }
        screening.setStatus("Pending");

        screenings.add(screening);
    }

    public List<Screening> getAllScreenings() {
        return new ArrayList<>(screenings);
    }

    public List<Screening> getScreeningsByMovie(String movieID) {
        List<Screening> result = new ArrayList<>();

        for(Screening screening : screenings) {
            if(screening.getMovieId().equals(movieID)) {
                result.add(screening);
            }
        }
        return result;
    }

    public List<Screening> getApprovedScreenings() {
        List<Screening> result = new ArrayList<>();

        for(Screening screening : screenings) {

            if(screening.getStatus().equals("Approved") && screening.getShowDateTime().isAfter(LocalDateTime.now())) {
                result.add(screening);
            }
        }
        return  result;
    }

    public boolean hasUpcomingScreenings(String movieId) {

        for(Screening screening : screenings) {
            if(screening.getMovieId().equals(movieId) && screening.getShowDateTime().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }
}
