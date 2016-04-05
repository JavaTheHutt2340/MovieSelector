package javathehutt.buzz_movieselector.model;

import android.content.Context;

import com.facebook.CallbackManager;

import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;
import javathehutt.buzz_movieselector.movie.MovieSource;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

/**
 * Created by Mohammed on 3/21/2016.
 */
public class DependencyInjectionContainer implements DependencyContainer {
    /**
     * the movie source
     */
    private static MovieSource ms;
    /**
     * the movie rating manager
     */
    private static MovieRatingManager mr;
    /**
     * the database interfacer
     */
    private static DatabaseHelper dh;
    /**
     * the context
     */
    private Context c;

    /**
     * constructor
     * @param context the context
     */
    public DependencyInjectionContainer(Context context) {
        this.c = context;
    }

    @Override
    public DatabaseHelper getDatabaseDep() {
        if (dh == null || dh.getContext() != c) {
            dh = new DatabaseHelper(c);
        }
        return dh;
    }

    @Override
    public MovieSource getRottenTomDep() {
        if (ms == null || ms.getContext() != c) {
            ms = new RottenTomatoesJSON(c);
        }
        return ms;
    }

    @Override
    public MovieRatingManager getMovieRatingDep() {
        if (mr ==  null) {
            mr = new MovieMapRatingManager();
        }
        return mr;
    }

    @Override
    public CallbackManager getCallbackManagDep() {
        return CallbackManager.Factory.create();
    }
}
