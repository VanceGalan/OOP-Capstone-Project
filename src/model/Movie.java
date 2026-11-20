package model;
public class Movie implements Manageable{
    private String movieId;
    private String title;
    private String genre;
    private int durationMins;
    private String mtrcbRating;
    private String description;
    private String poster;
    // Getters / Setters — Team: fill in together on Day 1

    public Movie(String movieId, String title, String genre, int durationMins, String mtrcbRating, String description, String poster) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.durationMins = durationMins;
        this.mtrcbRating = mtrcbRating;
        this.description = description;
        this.poster = poster;
    }

    public String getMovieId() {
        return movieId;
    }
    public void setMovieId(String movieId){
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurationMins() {
        return durationMins;
    }

    public void setDurationMins(int durationMins) {
        this.durationMins = durationMins;
    }

    public String getMtrcbRating() {
        return mtrcbRating;
    }

    public void setMtrcbRating(String mtrcbRating) {
        this.mtrcbRating = mtrcbRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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
