package service;

import model.Movie;
import java.util.List;
import java.util.ArrayList;

public class MovieService {
    private final List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        if(movie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        if(getMovieById(movie.getMovieId()) != null) {
            throw new IllegalArgumentException("Movie ID already exists.");
        }
        movies.add(movie);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public List<Movie> getAllMovie() {
        return getAllMovie();
    }
    public Movie getMovieById(String movieId) {

        if(movieId == null) {
            return null;
        }

        for(Movie movie : movies) {
            if(movie.getMovieId().equals(movieId)) {
                return movie;
            }
        }
        return null;
    }

    public void updateMovie(Movie updatedMovie) {

        if(updatedMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }

        for(int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getMovieId().equals(updatedMovie.getMovieId())) {
                movies.set(i, updatedMovie);
                return;
            }
        }
        throw new IllegalArgumentException("Movie not found");
    }

    public boolean deleteMovie(String movieId) {

        if(movieId == null) {
            return false;
        }

        return movies.removeIf(movie -> movie.getMovieId().equals(movieId));
    }

    public boolean movieExists(String movieId) {
        return getMovieById(movieId) != null;
    }

    public int getMovie() {
        return movies.size();
    }
}
