package javathehutt.buzz_movieselector;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import javathehutt.buzz_movieselector.movie.Movie;
import javathehutt.buzz_movieselector.movie.RottenTomatoes;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity{
    public final String KEY = "yedukp76ffytfuy24zsqk7f5";
    public final String URL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=";
    private RequestQueue queue;
    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void searchMovieByName(String name) {
        String url = URL + KEY +"&q=" + name + "&page_limit=1";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        //printing first 500 chars of the response.  Only want to do this for debug

                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        JSONObject obj1 = null;
                        try {
                            obj1 = resp.getJSONObject("RestResponse");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        assert obj1 != null;
                        //From that object, we extract the array of actual data labeled result
                        JSONArray array = obj1.optJSONArray("result");
                        ArrayList<Movie> movies = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {

                            try {
                                //for each array element, we have to create an object
                                JSONObject jsonObject = array.getJSONObject(i);
                                assert jsonObject != null;
                                String title = jsonObject.optString("name");
                                int year = jsonObject.optInt("year");
                                String critics_rating = jsonObject.optString("critics_rating");
                                int critics_score = jsonObject.optInt("critics_score");
                                Movie m = new Movie(title, year, critics_rating, critics_score);
                                //save the object for later
                                movies.add(m);


                            } catch (JSONException e) {
                                Log.d("VolleyApp", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                        }
                        displayMovies(movies, "Search by Title");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

    }
    public void recentMovies() {
    }

    public Movie[] recentDVD() {
        return new Movie[0];
    }


    public void displayMovies(List<Movie> movies, String title){
    }
}
