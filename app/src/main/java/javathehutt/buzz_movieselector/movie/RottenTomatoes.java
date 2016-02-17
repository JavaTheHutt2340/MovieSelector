package javathehutt.buzz_movieselector.movie;

/**
 * Created by Mohammed on 2/16/2016.
 */

public interface RottenTomatoes {
    Movie[] searchMovieByName();
    Movie[] searchMovieByGenre();
    Movie[] recentMovies();
    Movie[] recentDVD();
}
