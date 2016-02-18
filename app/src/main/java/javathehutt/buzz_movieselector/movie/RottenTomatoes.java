package javathehutt.buzz_movieselector.movie;

/**
 * Created by Mohammed on 2/16/2016.
 */

public interface RottenTomatoes {
    public final String KEY = "yedukp76ffytfuy24zsqk7f5";
    public final String URL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json";
    /**
     * Method to return an array of Movies from a name search
     * @return Movie array
     */
    Movie[] searchMovieByName(String name);

    /**
     * Method to return an array of Movies from a genre search
     * @return Movie array
     */
    Movie[] recentMovies();

    /**
     * Method to return an array
     * @return
     */
    Movie[] recentDVD();
}
