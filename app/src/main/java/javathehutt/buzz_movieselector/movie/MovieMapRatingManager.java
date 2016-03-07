package javathehutt.buzz_movieselector.movie;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JasonGibson on 2/29/16.
 */
public class MovieMapRatingManager implements MovieRatingManager {
    private static Map<Movie, Float> ratingMap;

    public MovieMapRatingManager() {
        if (ratingMap == null) {
            ratingMap = new HashMap<>();
        }
    }

    @Override
    public void addRatedMovie(Movie s, float rating) {
        if (s == null) {
            throw new IllegalArgumentException("cannot add a null value to the map");
        }
        ratingMap.put(s, rating);
    }

    @Override
    public float getRating (Movie s) {
        if (s == null) {
            throw new IllegalArgumentException("cannot get a rating from a null string");
        }
        Float rating = ratingMap.get(s);
        return rating == null ? 0.0f : rating;
    }
}
