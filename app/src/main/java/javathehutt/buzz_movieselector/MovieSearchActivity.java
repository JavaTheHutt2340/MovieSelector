package javathehutt.buzz_movieselector;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MovieSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
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
        json = new RottenTomatoesJSON(MovieSearchActivity.this);
    }

    private RottenTomatoesJSON json;

    /**
     * Test method
     * DO NOT USE EXCEPT TO TEST
     */
    private void makeCall() {
        json.newMovieReleases(10);
        //this is how to make the call
        //you do not and should not use this method
        //this is only an example of how it should look
    }

}
