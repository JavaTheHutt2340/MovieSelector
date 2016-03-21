package javathehutt.buzz_movieselector.model;

import android.content.Context;

import com.facebook.CallbackManager;

import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;
import javathehutt.buzz_movieselector.movie.RottenTomatoes;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

/**
 * Created by Mohammed on 3/21/2016.
 */
public class DependencyInjectionContainer implements DependencyContainer {
    private static RottenTomatoes rt;
    private static CallbackManager cb;
    private static MovieRatingManager mr;
    private Context c;

    public DependencyInjectionContainer(Context c) {
        this.c = c;
    }

    @Override
    public DatabaseHelper getDatabaseDep() {
        return new DatabaseHelper(c);
    }

    @Override
    public RottenTomatoes getRottenTomDep() {
        return new RottenTomatoesJSON(c);
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
        if (cb == null) {
            cb = CallbackManager.Factory.create();
        }
        return cb;
    }
}
