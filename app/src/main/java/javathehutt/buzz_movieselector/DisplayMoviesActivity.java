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
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.movie.MovieSource;

/**
 * Activity to Display movies.
 *
 * @author Saqib Ali
 * @version 1.0
 */
public class DisplayMoviesActivity extends Activity {

    private static ListView displayMoviesView;
    private static ArrayAdapter<javathehutt
            .buzz_movieselector.movie.Movie> movieAdapter;
    private MovieSource rtjson;
    private int state;
    private DependencyContainer dc;
    private DatabaseHelper db;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = new DependencyInjectionContainer(this);
        db = dc.getDatabaseDep();
        setContentView(R.layout.activity_display_movies);
        displayMoviesView = (ListView) findViewById(R.id.displayMoviesView);
        movieAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<javathehutt.buzz_movieselector.movie.Movie>());
        displayMoviesView.setAdapter(movieAdapter);
        String searchText = (getIntent().getStringExtra("text"));
        this.registerReceiver(new Receiver(), new IntentFilter("test"));
        rtjson = dc.getRottenTomDep();
        Bundle bundle = getIntent().getExtras();
        state = bundle.getInt("key");
        u = db.lastLogIn();
        switch (state) {
        case 1:
            rtjson.newDVDReleases(12, 1, false);
            break;
        case 2:
            rtjson.searchMovieByName(searchText, 12, 1);
            break;
        case 3:
            rtjson.newMovieReleases(12, 1);
            break;
        case 4:
            rtjson.newDVDReleases(12, 1, true);
            break;
        default:
            break;
        }

    }

    /**
     * gets the context
     * @return the Context
     */
    public Context getContext() {
        return this;
    }

    /**
     * returns the array adapter
     * @return ArrayAdapter
     */
    public static ArrayAdapter getAdapter() {
        return movieAdapter;
    }

    /**
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

    private class Receiver extends BroadcastReceiver {
        private int count = 2;
        private final int totalNumber = 12;
        private final int increment = 12;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (state == 4) {
                Log.i("test2", "called");
                for (int i = 0; i < movieAdapter.getCount(); i++) {
                    if (!movieAdapter.getItem(i).containsGenre(u
                            .getFavoriteGenre())) {
                        movieAdapter.remove(movieAdapter.getItem(i));
                    }
                }
            }
            if (movieAdapter.getCount() < totalNumber && count < 100) {
                Log.i("test2", "count" + movieAdapter.getCount());
                switch (state) {
                case 1:
                    rtjson.newDVDReleases(increment, count++, false);
                    break;
                case 2:
                    String searchText = (getIntent().getStringExtra("text"));
                    rtjson.searchMovieByName(searchText, increment, count++);
                    break;
                case 3:
                    rtjson.newMovieReleases(increment, count++);
                    break;
                case 4:
                    if (movieAdapter.getCount() < 6) {
                        rtjson.newDVDReleases(increment, count++, true);
                    }
                    break;
                default:
                    break;
                }
            }
        }
    }
}
