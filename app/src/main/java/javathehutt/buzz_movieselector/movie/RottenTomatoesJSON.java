package javathehutt.buzz_movieselector.movie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

import javathehutt.buzz_movieselector.MainMenuActivity;
import javathehutt.buzz_movieselector.MovieSearchActivity;
import javathehutt.buzz_movieselector.DisplayMoviesActivity;


/**
 * Class using Volley in order to access Movie objects
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements RottenTomatoes {
    private static RequestQueue queue;
    Context context;
    /**
     * Constructor for a RottenTomatoesJSON interfacer
     * @param context
     */
    public RottenTomatoesJSON(Context context) {
        this.context = context;
        if (null == queue) {
            queue = Volley.newRequestQueue(context);
        }
    }
    /**
     * Method to call for new Movie Releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     */
    @Override
    public void newMovieReleases(int limit) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey="
                        + KEY + "&limit=" + limit;
        passOnMoviesList(url);
    }
    /**
     * Method to call for new DVD movie releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     */
    @Override
    public void newDVDReleases(int limit) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey="
                        + KEY + "&page_limit=" + limit;
        passOnMoviesList(url);
    }

    /**
     * Method to search for movie based on name
     * @param name title of movie
     * @param limit most movies per page
     */
    @Override
    public void searchMovieByName(String name, int limit) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException();
        }
        String[] nameParts = name.split(" ");
        name = "";
        for(int i = 0; i < nameParts.length - 1; i++) {
            name += nameParts[i] + "%20";
        }
        name += nameParts[nameParts.length - 1];
        String url = URL + KEY +"&q=" + name + "&page_limit=" + limit;
        passOnMoviesList(url);
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
                            //Log.i("test", resp.names() + "");
                            //Log.i("test", resp.getString("movies") + "");
                            array = resp.getJSONArray("movies");
                        } catch (JSONException e) {
                            //Log.i("test", "fail");
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
                                String synopsis = jsonObject.optString("synopsis");
                                JSONObject rating = jsonObject.getJSONObject("ratings");
                                String critics_rating = rating.optString("critics_rating");
                                int critics_score = rating.optInt("critics_score");
                                Movie m = new Movie(title, year, critics_rating, critics_score, synopsis);
                                //save the object for later
                                movies.add(m);
                            } catch (JSONException e) {
                                Log.i("test", "fail");
                                Log.d("VolleyApp", "Failed to get JSON object");
                                Log.d("test", e.getStackTrace().toString());
                                e.printStackTrace();
                            }
                        }
                        //sends movies list to method to be formatted for xml layout
                        displayMovies(movies);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);
    }

    /**
     * Called when request from requestQuery is completed
     *  with new movies to display
     * @param movies List of Movie to display
     */
    private void displayMovies(List<Movie> movies) {
        ArrayAdapter adapter = new ArrayAdapter(context,
                android.R.layout.simple_list_item_1, movies);
        ListView listView = DisplayMoviesActivity.getListView();
        listView.setAdapter(adapter);
    }
}
