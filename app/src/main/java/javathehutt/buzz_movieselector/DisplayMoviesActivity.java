package javathehutt.buzz_movieselector;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
public class DisplayMoviesActivity extends AppCompatActivity {

    ListView displayMoviesView;
    Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayMoviesView = (ListView) findViewById(R.id.displayMoviesView);
        RottenTomatoesJSON rottenTomatoesJSON = new RottenTomatoesJSON(context);
        rottenTomatoesJSON.newMovieReleases(10);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public Context getContext() {
        return context;
    }

    public ListView getListView() {
        return displayMoviesView;
    }
}
