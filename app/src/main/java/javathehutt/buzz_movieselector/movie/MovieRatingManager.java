package javathehutt.buzz_movieselector.movie;

/**
 * Created by JasonGibson on 2/29/16.
 */
public interface MovieRatingManager {
    /**
    * adds a rating for a movie to the map
    * @param m the movie
    * @param rating given rating
     */
    void addRatedMovie(Movie m, float rating);

    /**
    * gets a rating for a movie
    * @param m the movie
    * @return the rating of the movie
     */
    float getRating(Movie m);


}
