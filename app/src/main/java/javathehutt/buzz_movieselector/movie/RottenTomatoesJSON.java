package javathehutt.buzz_movieselector.movie;


import android.content.Context;
import android.content.Intent;
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
import java.util.Comparator;
import java.util.List;

import javathehutt.buzz_movieselector.DisplayMoviesActivity;
import javathehutt.buzz_movieselector.MovieViewActivity;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.User;


/**
 * Class using Volley in order to access Movie objects
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements MovieSource {
    /**
     * private utility boolean String
     */
    private static final String TRUE_STRING = "true";
    /**
     * the rest queue
     */
    private static RequestQueue queue;
    /**
     * the context
     */
    private Context context;
    /**
     * the adapter for the movies
     */
    private ArrayAdapter<Movie> adapter;
    /**
     * the current user
     */
    private User u;
    /**
     * Constructor for a RottenTomatoesJSON interfacer
     * @param c the context
     */
    public RottenTomatoesJSON(Context c) {
        adapter = DisplayMoviesActivity.getAdapter();
        this.context = c;
        final DependencyContainer dc = new DependencyInjectionContainer(context);
        if (null == queue) {
            queue = Volley.newRequestQueue(context);
        }
        u = dc.getDatabaseDep().lastLogIn();
    }
    /**
     * Method to call for new Movie Releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     * @param page which page out of all pages to use
     */
    @Override
    public final void newMovieReleases(int limit, int page) {
        final String url =
                "http://api.rottentomatoes.com/api/public/v1."
                        + "0/lists/movies/opening.json?apikey="
                        + KEY + "&limit=" + limit + "&page=" + page;
        passOnMoviesList(url, false);
    }
    /**
     * Method to call for new DVD movie releases
     * Generates URL, sends into passOnMoviesList()
     * @param limit most movies per page
     * @param page which page out of all pages to use
     * @param b to passOn
     */
    @Override
    public final void newDVDReleases(int limit, int page, boolean b) {
        final String url =
                "http://api.rottentomatoes.com/api/public"
                        + "/v1.0/lists/dvds/new_releases.json?apikey="
                        + KEY + "&page_limit=" + limit + "&page=" + page;
        passOnMoviesList(url, b);
    }

    /**
     * Method to search for movie based on name
     * @param name title of movie
     * @param limit most movies per page
     * @param page which page out of all pages to use
     */
    @Override
    public final void searchMovieByName(String name, int limit, int page) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Please give valid"
                    + " name");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit should be"
                    + " positive");
        }
        if (page <= 0) {
            throw new IllegalArgumentException("Page number should"
                    +" be positive");
        }
        final String[] nameParts = name.split(" ");
        //name = "";
        String result = "";
        for (int i = 0; i < nameParts.length - 1; i++) {
            result += nameParts[i] + "%20";
        }
        result += nameParts[nameParts.length - 1];
        final String url = URL + KEY + "&q=" + result + "&page_limit="
                + limit  + "&page=" + page;
        passOnMoviesList(url, false);
    }

    /**
     * gets a similar movie
     * @param url the url to search
     */
    public final void similarMovies(String url) {
        String result = url;
        result = result.substring(0, result.indexOf(".json")); // remove .json
        result += "/similar.json?apikey=" + KEY; //create the correct URL
        result = "http:" + result;
        adapter.clear();
        passOnMoviesList(result, true, true);
    }

    /**
     * Sets on Volley queue JsonObjectRequest to retrieve movie information
     *  if information is obtained, create Movie list
     *  , and pass on to displayMovies
     * @param url specific String URL based on specific movie to view
     * @param filter the filer
     */
    private void passOnMoviesList(String url, final boolean ... filter) {

        final boolean f = filter.length < 1 && filter[1];
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, "", new MovieResponse(filter, f) , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsObjRequest);
    }

    /**
     * gets a similar movie
     * @param m Movie
     */
    public final void similarMovies(Movie m) {
        final String url = m.getApiUrl();
        similarMovies(url);
    }

    /**
     * Called when request from requestQuery is completed
     *  with new movies to display
     * @param movie a Movie to display
     */
    private void displayMovie(Movie movie) {
        if (movie != null) {
            adapter.add(movie);
            adapter.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie lhs, Movie rhs) {
                    return rhs.getCriticsScoreInt() - lhs.getCriticsScoreInt();
                }
            });
            final ListView listView = DisplayMoviesActivity.getListView();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                        long arg3) {
                    //Here pos is the position of row clicked
                    final Intent ratingsIntent = new Intent(context, MovieViewActivity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putSerializable("object", adapter.getItem(pos));
                    ratingsIntent.putExtras(bundle);
                    ratingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(ratingsIntent);
                }
            });
        }
    }

    /**
     * Context accessor method
     * @return Context used to create RTJSON
     */
    public final Context getContext() {
        return context;
    }

    private class Task extends AsyncTask<String, Movie, Void> {

        @Override
        protected Void doInBackground(final String... params) {
            final Long l = Long.valueOf(params[1]) * 75;
            try {
                if (params[1] != null) {
                    Thread.sleep(l);
                }
            } catch (InterruptedException e) {
                Log.e("Async", e.getStackTrace().toString());
            }
            final String url = "http://api.rottentomatoes.com/api/public"
                    + "/v1.0/movies/" + params[0] + ".json?apikey=" + KEY;
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET, url, "", new ResponseListener(params, l) , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("volley", "failure");
                        }
                    });
            queue.add(jsObjRequest);
            return null;
        }

        @Override
        protected void onProgressUpdate(Movie... params) {
            displayMovie(params[0]);
        }

        private class ResponseListener implements Response.Listener<JSONObject> {
            private String[] params;
            private Long l;
            public ResponseListener(String[] passedIn, Long passedInLong) {
                params = passedIn;
                l = passedInLong;
            }

            @Override
            public void onResponse(JSONObject resp) {
                try {
                    assert resp != null;
                    final String title = resp.optString("title");
                    final int year = resp.optInt("year");
                    final String synopsis = resp.optString("synopsis");
                    final JSONObject rating = resp
                            .getJSONObject("ratings");
                    final String criticsRating = rating
                            .optString("critics_rating");
                    final int criticsScore = rating
                            .optInt("critics_score");
                    final String genre = resp.optString("genres");
                    final String url = resp.getJSONObject("links")
                            .getString("self");
                    final String altUrl = resp.getJSONObject("links")
                            .getString("alternate");
                    final Movie m = new Movie(title, year, criticsRating,
                            criticsScore, synopsis, url, genre);
                    m.setAltUrl(altUrl);
                    if (TRUE_STRING.equals(params[3])) {
                        publishProgress(m);
                    }
                    if (TRUE_STRING.equals(params[2])) {
                        if (m.containsGenre(u.getFavoriteGenre())) {
                            publishProgress(m);
                        }
                    } else {
                        publishProgress(m);
                    }
                } catch (JSONException e) {
                    Log.d("volley", "Failed to get JSON object");
                }
                if (l.equals(Long.valueOf("11") * 75)) {
                    final Intent i = new Intent();
                    i.setAction("test");
                    context.sendBroadcast(i);
                }
            }
        }
    }

    private class MovieResponse implements Response.Listener<JSONObject> {

        private boolean[] filter;
        private boolean f;
        public MovieResponse(boolean[] boolArray, boolean bool) {
            filter = boolArray;
            f = bool;
        }

        @Override
        public void onResponse(JSONObject resp) {
            JSONArray array = null;
            try {
                array = resp.getJSONArray("movies");
            } catch (JSONException e) {
                Log.e("JSON", "error");
            }
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                try {
                    final JSONObject jsonObject = array.getJSONObject(i);
                    assert jsonObject != null;
                    list.add(jsonObject.optString("id"));
                } catch (JSONException e) {
                    Log.d("VolleyApp", "Failed to get JSON object");
                }
            }
            for (int i = 0; i < list.size(); i++) {
                final Task task = new Task();
                task.execute(list.get(i), Integer.toString(i), filter[0] ? TRUE_STRING
                        : "false", f ? TRUE_STRING : "false");
            }
        }
    }
}
