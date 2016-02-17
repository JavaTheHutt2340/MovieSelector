package javathehutt.buzz_movieselector.movie;

/**
 * Created by Mohammed on 2/16/2016.
 */

public interface RottenTomatoes {
    /**
     * Method to return an array of Movies from a name search
     * @return Movie array
     */
    Movie[] searchMovieByName(String name);

    /**
     * Method to return an array of Movies from a genre search
     * @return Movie array
     */
    Movie[] searchMovieByGenre();

    /**
     * Method to return an array of recently released
     *  in theaters movies
     * @return Movie array
     */
    Movie[] recentMovies();

    /**
     * Method to return an array
     * @return
     */
    Movie[] recentDVD();
}
