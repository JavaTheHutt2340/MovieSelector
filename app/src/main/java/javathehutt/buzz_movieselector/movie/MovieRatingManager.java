package javathehutt.buzz_movieselector.movie;

/**
 * Created by JasonGibson on 2/29/16.
 */
public interface MovieRatingManager {
    /*
    * adds a rating for a movie to the map
    * @param the title and year of the movie
    * @param the given rating
     */
    void addRatedMovie(Movie m, float rating);

    /*
    * gets a rating for a movie
    * @param s the title and year of the movie
     */
    float getRating(Movie m);


}
