package javathehutt.buzz_movieselector.movie;

/**
 * Interface for interfacing with RottenTomatoes
 * Created by Mohammed on 2/16/2016.
 */

public interface RottenTomatoes {
    public final String KEY = "yedukp76ffytfuy24zsqk7f5";
    public final String URL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=";
    /**
     * Method to send a list of Movies from a name search to an Intent
     */
    void searchMovieByName(String name, int limit);

    /**
     * Method to send a List of Movie from recent movies query to an Intent
     */
    void newMovieReleases(int limit);

    /**
     * Method to send a List of Movie from recent DVD releases to an Intent
     */
    void newDVDReleases(int limit);
}
