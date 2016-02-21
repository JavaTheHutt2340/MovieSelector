package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Adapter;

import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

/**
 * Activity to Display movies.
 *
 * @author Saqib Ali
 * @version 1.0
 */
public class DisplayMoviesActivity extends Activity {

    static ListView displayMoviesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        displayMoviesView = (ListView) findViewById(R.id.displayMoviesView);
        String searchText = (getIntent().getStringExtra("text"));
        RottenTomatoesJSON RTJSON = new RottenTomatoesJSON(this);
        Bundle bundle = getIntent().getExtras();
        int state = bundle.getInt("key");
        switch(state) {
            case 1:
                RTJSON.newDVDReleases(12);
                break;
            case 2:
                RTJSON.searchMovieByName(searchText, 12);
                break;
            case 3:
                RTJSON.newMovieReleases(12);
                break;
        }

    }

    /*
    * gets the context
    * @return the Context
     */
    public Context getContext() {
        return this;
    }

    /*
    * gets the list view
    * @return displayMovieListView
     */
    public static ListView getListView() {
        return displayMoviesView;
    }
}
