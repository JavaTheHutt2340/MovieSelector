package javathehutt.buzz_movieselector.movie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javathehutt.buzz_movieselector.DisplayMoviesActivity;
import javathehutt.buzz_movieselector.MovieViewActivity;


/**
 * Class using Volley in order to access Movie objects
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements RottenTomatoes {
    private static RequestQueue queue;
    //private static RequestQueue queue2;
    Context context;
    Intent ratingsIntent;
    /**
     * Constructor for a RottenTomatoesJSON interfacer
     * @param context
     */
    public RottenTomatoesJSON(Context context) {
        this.context = context;
        if (null == queue) {
            queue = Volley.newRequestQueue(context);
        }
        /*if (queue2 == null) {
            queue2 = Volley.newRequestQueue(context);
        }*/
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
        passOnMoviesList(url);
    }
    /**
     * Method to call for new DVD movie releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     */
    @Override
    public void newDVDReleases(int limit, int page) {
        String url =
                "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey="
                        + KEY + "&page_limit=" + limit + "&page=" + page;
        passOnMoviesList(url);
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
        passOnMoviesList(url);
    }

        /**
         * Sets on Volley queue JsonObjectRequest to retrieve movie information
         *  if information is obtained, create Movie list, and pass on to displayMovies
         * @param url specific String URL based on specific movie to view
         */
    private void passOnMoviesList(String url) {
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
                            task.execute(list.get(i), i + "");
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
        String[] temp = url.split(".json");

    }

    /**
     * Called when request from requestQuery is completed
     *  with new movies to display
     * @param movies List of Movie to display
     */
    private void displayMovies(final Set<Movie> movies) {
        for(Movie m : movies) {
            Log.i("print", m.getName());
        }
        final ArrayAdapter<Movie> adapter = DisplayMoviesActivity.getAdapter();
        adapter.addAll(movies);
        ListView listView = DisplayMoviesActivity.getListView();
        listView.setAdapter(adapter);
        Intent i = new Intent();
        i.setAction("test");
        context.sendBroadcast(i);
        Log.i("test2", "message broadcast");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                //Here pos is the position of row clicked
                ratingsIntent = new Intent(context, MovieViewActivity.class);
                Bundle bundle = new Bundle();
        bundle.putSerializable("object", adapter.getItem(pos));
                ratingsIntent.putExtras(bundle);
                context.startActivity(ratingsIntent);
            }
        });
    }

    /**
     * Called when request from requestQuery is completed
     *  with new movies to display
     * @param movie a Movie to display
     */
    private void displayMovie(Movie movie) {
        final ArrayAdapter<Movie> adapter = DisplayMoviesActivity.getAdapter();
        adapter.add(movie);
        ListView listView = DisplayMoviesActivity.getListView();
        listView.setAdapter(adapter);
        Intent i = new Intent();
        i.setAction("test");
        context.sendBroadcast(i);
        Log.i("test2", "message broadcast");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                //Here pos is the position of row clicked
                ratingsIntent = new Intent(context, MovieViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", adapter.getItem(pos));
                ratingsIntent.putExtras(bundle);
                context.startActivity(ratingsIntent);
            }
        });
    }


    private class Task extends AsyncTask<String, Movie, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Log.i("task", params[1]);
                if (params[1] != null) {
                    Thread.sleep(Long.valueOf(params[1]) * 75);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + params[0] + ".json?apikey=" + KEY;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            Log.i("test2", "rest response received for individual movie");
                            //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                            //From that object, we extract the array of actual data labeled result
                            try {
                                //for each array element, we have to create an object
                                assert resp != null;
                                //Log.i("test", resp.names().toString());
                                String title = resp.optString("title");
                                int year = resp.optInt("year");
                                String synopsis = resp.optString("synopsis");
                                JSONObject rating = resp.getJSONObject("ratings");
                                String critics_rating = rating.optString("critics_rating");
                                int critics_score = rating.optInt("critics_score");
                                Movie m = new Movie(title, year, critics_rating, critics_score, synopsis, null);
                                publishProgress(m);
                            } catch (JSONException e) {
                                Log.d("volley", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                            //sends movies list to method to be formatted for xml layout
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

        /*@Override
        protected void onPostExecute(Result) {

        }*/
    }
}
