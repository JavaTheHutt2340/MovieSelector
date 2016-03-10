package javathehutt.buzz_movieselector.movie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javathehutt.buzz_movieselector.DisplayMoviesActivity;
import javathehutt.buzz_movieselector.MovieViewActivity;
import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.User;


/**
 * Class using Volley in order to access Movie objects
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements RottenTomatoes {
    private static RequestQueue queue;
    Context context;
    Intent ratingsIntent;
    private ArrayAdapter<Movie> adapter;
    User u;
    /**
     * Constructor for a RottenTomatoesJSON interfacer
     * @param context
     */
    public RottenTomatoesJSON(Context context) {
        adapter = DisplayMoviesActivity.getAdapter();
        this.context = context;
        if (null == queue) {
            queue = Volley.newRequestQueue(context);
        }
        u = new DatabaseHelper(context).lastLogIn();
    }
    /**
     * Method to call for new Movie Releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     */
    @Override
    public void newMovieReleases(int limit, int page) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey="
                        + KEY + "&limit=" + limit + "&page=" + page;
        passOnMoviesList(url, false);
    }
    /**
     * Method to call for new DVD movie releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     */
    @Override
    public void newDVDReleases(int limit, int page, boolean b) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey="
                        + KEY + "&page_limit=" + limit + "&page=" + page;
        passOnMoviesList(url, b);
    }

    /**
     * Method to search for movie based on name
     * @param name title of movie
     * @param limit most movies per page
     */
    @Override
    public void searchMovieByName(String name, int limit, int page) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException();
        }
        String[] nameParts = name.split(" ");
        name = "";
        for(int i = 0; i < nameParts.length - 1; i++) {
            name += nameParts[i] + "%20";
        }
        name += nameParts[nameParts.length - 1];
        String url = URL + KEY +"&q=" + name + "&page_limit=" + limit  + "&page=" + page;
        passOnMoviesList(url, false);
    }

    public void similarMovies(String url) {
        url = url.substring(0, url.indexOf(".json")); // remove .json
        url += "/similar.json?apikey=" + KEY; //create the correct URL
        url = "http:" + url;
        adapter.clear();
        passOnMoviesList(url, true, true);
    }

        /**
         * Sets on Volley queue JsonObjectRequest to retrieve movie information
         *  if information is obtained, create Movie list, and pass on to displayMovies
         * @param url specific String URL based on specific movie to view
         */
    private void passOnMoviesList(String url, final boolean ... filter) {
        final boolean f = filter.length >= 1 ? false : filter[1];
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //context.registerReceiver(new Receiver(), new IntentFilter("test2"));
                        Log.i("test", "rest response received");
                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        JSONArray array = null;
                        try {
                            //Log.i("test", resp.getString("movies") + "");
                            array = resp.getJSONArray("movies");
                           // Log.i("test", array. + "");
                        } catch (JSONException e) {
                            //Log.i("test", "fail");
                            e.printStackTrace();
                        }
                        //From that object, we extract the array of actual data labeled result
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                //for each array element, we have to create an object
                                JSONObject jsonObject = array.getJSONObject(i);
                                assert jsonObject != null;
                                list.add(jsonObject.optString("id"));
                            } catch (JSONException e) {
                                Log.d("VolleyApp", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            Task task = new Task();
                            task.execute(list.get(i), i + "", filter[0] ? "true" : "false"
                                    , f ? "true" : "false");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);
    }

    public void similarMovies(Movie m) {
        String url = m.getUrl();
        similarMovies(url);

    }

    /**
     * Called when request from requestQuery is completed
     *  with new movies to display
     * @param movie a Movie to display
     */
    private void displayMovie(Movie movie) {;
        adapter.add(movie);
        adapter.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.getCriticsScoreInt() - lhs.getCriticsScoreInt();
            }
        });
        ListView listView = DisplayMoviesActivity.getListView();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                //Here pos is the position of row clicked
                ratingsIntent = new Intent(context, MovieViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", adapter.getItem(pos));
                ratingsIntent.putExtras(bundle);
                ratingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // added this because it starts activity outside of class
                context.startActivity(ratingsIntent);
            }
        });
    }


    private class Task extends AsyncTask<String, Movie, Void> {

        @Override
        protected Void doInBackground(final String... params) {
            final Long l = Long.valueOf(params[1]) * 75;
            try {
                Log.i("task", params[1]);
                if (params[1] != null) {
                    Thread.sleep(l);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + params[0] + ".json?apikey=" + KEY;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            //Log.i("test2", "rest response received for individual movie");
                            //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                            //From that object, we extract the array of actual data labeled result
                            try {
                                //for each array element, we have to create an object
                                assert resp != null;
                                String title = resp.optString("title");
                                int year = resp.optInt("year");
                                String synopsis = resp.optString("synopsis");
                                JSONObject rating = resp.getJSONObject("ratings");
                                String critics_rating = rating.optString("critics_rating");
                                int critics_score = rating.optInt("critics_score");
                                String genre = resp.optString("genres");
                                String url = resp.getJSONObject("links").getString("self");
                                Movie m = new Movie(title, year, critics_rating, critics_score, synopsis, url, genre);
                                if (params[3].equals("true")) {
                                    publishProgress(m);
                                }
                                if (params[2].equals("true")) {
                                    if (m.containsGenre(u.getFavoriteGenre())) {
                                        publishProgress(m);
                                    }
                                } else {
                                    publishProgress(m);
                                }
                            } catch (JSONException e) {
                                Log.d("volley", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                            if(l.equals(Long.valueOf("11") * 75)) {
                                Intent i = new Intent();
                                i.setAction("test");
                                context.sendBroadcast(i);
                                Log.i("test2", "message broadcast");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("volley", "failure");
                        }
                    });
            queue.add(jsObjRequest);
            return null;
        }

        @Override
        protected void onProgressUpdate(Movie... params) {
            displayMovie(params[0]);
            Log.i("task", params[0].getName());
        }
    }
}
