package javathehutt.buzz_movieselector.movie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import javathehutt.buzz_movieselector.MovieSearchActivity;


/**
 * Class using HttpRequest class custom library in order to access Movie objects
 * Deprecated in favor of Volley?
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON {
    public final String KEY = "yedukp76ffytfuy24zsqk7f5";
    public final String URL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=";
    private static RequestQueue queue;

    public RottenTomatoesJSON(Context context) {
        if (null == queue) {
            queue = Volley.newRequestQueue(context);
        }
    }
    /**
     * Method to call for new Movie Releases
     * Generates URL, sends into passOnMoviesList()
     * TODO: associate with button
     */
    public void newMovieReleases(int limit) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey="
                        + KEY + "&limit=" + limit;
        passOnMoviesList(url);
    }
    /**
     * Method to call for new DVD movie releases
     * Generates URL, sends into passOnMoviesList()
     * TODO: associate with button
     */
    public void newDVDReleases(int limit) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey="
                        + KEY + "&page_limit=" + limit;
        passOnMoviesList(url);
    }

    /**
     * Method to search for movie based on name
     * TODO: associate with button and search field, remove name parameter
     * @param name title of movie
     */
    public void searchMovieByName(String name, int limit) {
        String url = URL + KEY +"&q=" + name + "&page_limit=" + limit;
        passOnMoviesList(url);
    }

    public HttpRequest makeRequestByName(String name){
        //http://api.rottentomatoes.com/api/public/v1.0/movies.json
        //yedukp76ffytfuy24zsqk7f5
        try {
            return HttpRequest.get(URL, true,
                    "apikey", KEY, "q", name);
        } catch (HttpRequest.HttpRequestException e) {
            return null;
        }
    }
    public HttpRequest makeRequestByGenre() {
        return null;
    }
    public InputStreamReader extractReader(HttpRequest h) {
        try {
            if (h.ok()) {
                return h.reader();
            }
            return null;
        } catch (HttpRequest.HttpRequestException e) {
            return null;
        }
    }

        /**
         * Sets on Volley queue JsonObjectRequest to retrieve movie information
         *  if information is obtained, create Movie list, and pass on to displayMovies
         * @param url specific String URL based on specific movie to view
         */
    public void passOnMoviesList(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        //printing first 500 chars of the response.  Only want to do this for debug

                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        JSONArray array = null;
                        try {
                            Log.i("test", resp.names() + "");
                            //Log.i("test", resp.getString("movies") + "");
                            array = resp.getJSONArray("movies");
                        } catch (JSONException e) {
                            Log.i("test", "fail");
                            e.printStackTrace();
                        }
                        //From that object, we extract the array of actual data labeled result
                        ArrayList<Movie> movies = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                //for each array element, we have to create an object
                                JSONObject jsonObject = array.getJSONObject(i);
                                assert jsonObject != null;
                                String title = jsonObject.optString("title");
                                int year = jsonObject.optInt("year");
                                String critics_rating = jsonObject.optString("critics_rating");
                                int critics_score = jsonObject.optInt("critics_score");
                                Movie m = new Movie(title, year, critics_rating, critics_score);
                                //save the object for later
                                movies.add(m);
                            } catch (JSONException e) {
                                Log.i("test", "fail");
                                Log.d("VolleyApp", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                        }
                        //sends movies list to method to be formated for xml layout
                        displayMovies(movies);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);
    }

    public void displayMovies(List<Movie> movies) {
        Log.i("test", movies.toString());
    }
}
