package javathehutt.buzz_movieselector.movie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JasonGibson on 2/29/16.
 */
public class MovieMapRatingManager implements MovieRatingManager {
    /**
     * the map pf movies to ratings
     */
    private static Map<Movie, Float> ratingMap;

    /**
     * constructor for class
     */
    public MovieMapRatingManager() {
        if (ratingMap == null) {
            ratingMap = new HashMap<>();
        }
    }

    @Override
    public void addRatedMovie(Movie s, float rating) {
        if (s == null) {
            throw new IllegalArgumentException("can"
                    + "not add a null value to the map");
        }
        ratingMap.put(s, rating);
    }

    @Override
    public float getRating(Movie s) {
        if (s == null) {
            throw new IllegalArgumentException("cannot get a "
                    + "rating from a null string");
        }
        final Float rating = ratingMap.get(s);
        return rating == null ? 0.0f : rating;
    }
}
