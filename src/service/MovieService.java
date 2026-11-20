package service;

import model.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * In-memory business service for managing Movie catalog entries.
 */
public class MovieService {
    private final List<Movie> movies = new ArrayList<>();

    public String generateNextMovieId() {
        int nextNumber = 1;
        while (movieExists(String.format("MOV-%03d", nextNumber))) {
            nextNumber++;
        }
        return String.format("MOV-%03d", nextNumber);
    }

    public void addMovie(Movie movie) {
        validateMovie(movie);
        if (getMovieById(movie.getMovieId()) != null) {
            throw new IllegalArgumentException("Movie ID already exists.");
        }
        movies.add(movie);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    /**
     * Kept for compatibility with callers referencing the older singular name.
     */
    public List<Movie> getAllMovie() {
        return getAllMovies();
    }

    public Movie getMovieById(String movieId) {
        if (isBlank(movieId)) {
            return null;
        }
        for (Movie movie : movies) {
            if (Objects.equals(movie.getMovieId(), movieId)) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> searchMovies(String query) {
        if (isBlank(query)) {
            return getAllMovies();
        }
        String normalized = query.trim().toLowerCase(Locale.ENGLISH);
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (containsIgnoreCase(movie.getTitle(), normalized)
                    || containsIgnoreCase(movie.getGenre(), normalized)
                    || containsIgnoreCase(movie.getMtrcbRating(), normalized)) {
                results.add(movie);
            }
        }
        return results;
    }

    public void updateMovie(Movie updatedMovie) {
        validateMovie(updatedMovie);
        for (int i = 0; i < movies.size(); i++) {
            if (Objects.equals(movies.get(i).getMovieId(), updatedMovie.getMovieId())) {
                movies.set(i, updatedMovie);
                return;
            }
        }
        throw new IllegalArgumentException("Movie not found.");
    }

    public boolean deleteMovie(String movieId) {
        if (isBlank(movieId)) {
            return false;
        }
        return movies.removeIf(movie -> Objects.equals(movie.getMovieId(), movieId));
    }

    public boolean movieExists(String movieId) {
        return getMovieById(movieId) != null;
    }

    public int getMovieCount() {
        return movies.size();
    }

    /**
     * Kept for compatibility with earlier callers.
     */
    public int getMovie() {
        return getMovieCount();
    }

    private void validateMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        if (isBlank(movie.getMovieId())) {
            throw new IllegalArgumentException("Movie ID is required.");
        }
        if (isBlank(movie.getTitle())) {
            throw new IllegalArgumentException("Movie title is required.");
        }
        if (isBlank(movie.getGenre())) {
            throw new IllegalArgumentException("Movie genre is required.");
        }
        if (movie.getDurationMins() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0 minutes.");
        }
        if (isBlank(movie.getMtrcbRating())) {
            throw new IllegalArgumentException("MTRCB rating is required.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean containsIgnoreCase(String field, String normalizedQuery) {
        return field != null && field.toLowerCase(Locale.ENGLISH).contains(normalizedQuery);
    }
}
