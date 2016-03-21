package javathehutt.buzz_movieselector.model;

import com.facebook.CallbackManager;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;
import javathehutt.buzz_movieselector.movie.RottenTomatoes;

/**
 * Created by Mohammed on 3/21/2016.
 */
public interface DependencyContainer {
    /**
     * Method to get a DatabaseHelper object
     * @return DatabaseHelper
     */
    DatabaseHelper getDatabaseDep();

    /**
     * Method to get RottenTomatoes object
     * @return RottenTomatoes
     */
    RottenTomatoes getRottenTomDep();

    /**
     * Method to get a MovieRatingManager object
     * @return MovieRatingManager
     */
    MovieRatingManager getMovieRatingDep();

    /**
     * Method to get a Facebook CallbackManager
     * @return CallbackManager
     */
    CallbackManager getCallbackManagDep();
}
