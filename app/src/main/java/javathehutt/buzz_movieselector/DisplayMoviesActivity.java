package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.movie.RottenTomatoes;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

/**
 * Activity to Display movies.
 *
 * @author Saqib Ali
 * @version 1.0
 */
public class DisplayMoviesActivity extends Activity {

    static ListView displayMoviesView;
    private static ArrayAdapter<javathehutt.buzz_movieselector.movie.Movie> movieAdapter;
    private RottenTomatoes RTJSON;
    private int state;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        displayMoviesView = (ListView) findViewById(R.id.displayMoviesView);
        movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new ArrayList<javathehutt.buzz_movieselector.movie.Movie>());
        displayMoviesView.setAdapter(movieAdapter);
        String searchText = (getIntent().getStringExtra("text"));
        this.registerReceiver(new Receiver(), new IntentFilter("test"));
        RTJSON = new RottenTomatoesJSON(this);
        Bundle bundle = getIntent().getExtras();
        state = bundle.getInt("key");
        u = new DatabaseHelper(this).lastLogIn();
        switch(state) {
            case 1:
                RTJSON.newDVDReleases(12, 1, false);
                break;
            case 2:
                RTJSON.searchMovieByName(searchText, 12, 1);
                break;
            case 3:
                RTJSON.newMovieReleases(12, 1);
                break;
            case 4:
                RTJSON.newDVDReleases(12, 1, true);
                break;
        }

    }

    private class Receiver extends BroadcastReceiver {
        private int count = 2;
        private final int totalNumber = 12;
        private final int increment = 12;
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("test2", "message received " + state);
            if (state == 4) {
                Log.i("test2", "called");
                for (int i = 0; i < movieAdapter.getCount(); i++) {
                    if (!movieAdapter.getItem(i).containsGenre(u.getFavoriteGenre())) {
                        movieAdapter.remove(movieAdapter.getItem(i));
                    }
                }
            }
            if (movieAdapter.getCount() < totalNumber && count < 100) { //TODO REMEMBER TO UNCOMMENT ABOVE
                Log.i("test2", "count" + movieAdapter.getCount());
                switch(state) {
                    case 1:
                        RTJSON.newDVDReleases(increment, count++, false);
                        break;
                    case 2:
                        String searchText = (getIntent().getStringExtra("text"));//TODO INFINITE LOOP ISSUES HERE
                        RTJSON.searchMovieByName(searchText, increment, count++);
                        break;
                    case 3:
                        RTJSON.newMovieReleases(increment, count++);
                        break;
                    case 4:
                        if (movieAdapter.getCount() < 6) {
                            RTJSON.newDVDReleases(increment, count++, true);
                        }
                        break;
                }
            }
        }
    }

    /*
     * gets the context
     * @return the Context
     */
    public Context getContext() {
        return this;
    }

    public static ArrayAdapter getAdapter() {
        return movieAdapter;
    }

    /*
     * gets the list view
     * @return displayMovieListView
     */
    public static ListView getListView() {
        return displayMoviesView;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "DisplayMoviesActivity closed");
    }
}
