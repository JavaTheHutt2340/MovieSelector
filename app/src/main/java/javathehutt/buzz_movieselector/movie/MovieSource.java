package javathehutt.buzz_movieselector.movie;

import android.content.Context;

/**
 * Interface for interfacing with MovieSource
 * Created by Mohammed on 2/16/2016.
 */

public interface MovieSource {
    /**
     * RT api key
     */
    String KEY = "yedukp76ffytfuy24zsqk7f5";
    /**
     * url base case for rest calls
     */
    String URL = "http://api.rottentomatoes."
            + "com/api/public/v1.0/movies.json?apikey=";
    /**
     * Method to send a list of Movies from a name search to an Intent
     * @param name String representing movie to search
     * @param limit number of movies to take in
     * @param page the page to be checked
     */
    void searchMovieByName(String name, int limit, int page);

    /**
     * Method to send a List of Movie from recent movies query to an Intent
     * @param limit number of movies to take in
     * @param page the page to be checked
     */
    void newMovieReleases(int limit, int page);

    /**
     * Method to send a List of Movie from recent DVD releases to an Intent
     * @param limit number of movies to take in
     * @param b boolean for recommend
     * @param page the page to be checked
     */
    void newDVDReleases(int limit, int page, boolean b);

    /**
     * Uses a movie object to search for similar movies
     * @param m Movie
     */
    void similarMovies(Movie m);

    /**
     * Uses a String api url to search for similar movies
     * @param url the url to search
     */
    void similarMovies(String url);

    /**
     * return context used to create MovieSource object
     * @return Context
     */
    Context getContext();
}
