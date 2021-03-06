package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

    /**
     * the LitView that holds the movies
     */
    private static ListView displayMoviesView;
    /**
     * the array adapter for the listview
     */
    private static ArrayAdapter<javathehutt
            .buzz_movieselector.movie.Movie> movieAdapter;
    /**
     * the Movie Source
     */
    private MovieSource rtjson;
    /**
     * the state of the application
     */
    private int state;
    /**
     * the current user
     */
    private User u;
    /**
     * the starting number of movies to pull
     */
    private static final int STARTING_NUMBER = 20;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        final DatabaseHelper db = dc.getDatabaseDep();
        setContentView(R.layout.activity_display_movies);
        displayMoviesView = (ListView) findViewById(R.id.displayMoviesView);
        movieAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<javathehutt.buzz_movieselector.movie.Movie>());
        displayMoviesView.setAdapter(movieAdapter);
        final String searchText = (getIntent().getStringExtra("text"));
        this.registerReceiver(new Receiver(), new IntentFilter("test"));
        rtjson = dc.getRottenTomDep();
        final Bundle bundle = getIntent().getExtras();
        state = bundle.getInt("key");
        u = db.lastLogIn();
        switch (state) {
            case MovieSearchActivity.NEWDVD:
                movieAdapter.clear();
                rtjson.newDVDReleases(STARTING_NUMBER, 1, false);
                break;
            case MovieSearchActivity.BYNAME:
                movieAdapter.clear();
                rtjson.searchMovieByName(searchText, STARTING_NUMBER, 1);
                break;
            case MovieSearchActivity.INTHEATRE:
                movieAdapter.clear();
                rtjson.newMovieReleases(STARTING_NUMBER, 1);
                break;
            case MovieSearchActivity.RECOMMEND:
                movieAdapter.clear();
                rtjson.newDVDReleases(STARTING_NUMBER, 1, true);
                break;
            default:
                break;
        }

    }

    /**
     * gets the context
     * @return the Context
     */
    public final Context getContext() {
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
    public final void onBackPressed() {
        movieAdapter.clear();
        finish();
    }

    private class Receiver extends BroadcastReceiver {
        /**
         * Max number of movies to call
         */
        private static final int MAX_COUNT = 100;
        /**
         * page to start looking at
         */
        private int count = 2;
        /**
         * total number of movies needed to not pull more
         */
        private static final int TOTAL_NUMBER = 12;
        /**
         * how many movies to pull extra
         */
        private static final int INCREMENT = 20;

        /**
         * returns if the program should continue
         * @return true is yes
         */
        private boolean isValid() {
            return movieAdapter.getCount() < TOTAL_NUMBER && count < MAX_COUNT;
        }

        @Override
        public void onReceive(Context context,
                              Intent intent) {
            if (state == MovieSearchActivity.RECOMMEND) {
                for (int i = 0; i < movieAdapter.getCount(); i++) {
                    if (!movieAdapter.getItem(i).containsGenre(u
                            .getFavoriteGenre())) {
                        movieAdapter.remove(movieAdapter.getItem(i));
                    }
                }
            }
            if (isValid()) {
                switch (state) {
                    case MovieSearchActivity.NEWDVD:
                        rtjson.newDVDReleases(INCREMENT, count++, false);
                        break;
                    case MovieSearchActivity.BYNAME:
                        final String searchText = (getIntent().getStringExtra("text"));
                        rtjson.searchMovieByName(searchText, INCREMENT, count++);
                        break;
                    case MovieSearchActivity.INTHEATRE:
                        rtjson.newMovieReleases(INCREMENT, count++);
                        break;
                    case MovieSearchActivity.RECOMMEND:
                        rtjson.newDVDReleases(INCREMENT, count++, true);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
