package javathehutt.buzz_movieselector.movie;

/**
 * Created by JasonGibson on 2/29/16.
 */
public interface MovieRatingManager {
    void addRatedMovie(String s, float rating);
    float getRating(String s);
}
