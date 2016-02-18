/**
 * Class to do searches and instantiate REST calls
 * @author Mohammed Saqib
 * @version 1.0
 */
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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




    /**
     * TODO: Create new Intent, Adapter for movies, and fragments for each movie
     * @param movies
     */
    public void displayMovies(List<Movie> movies){
    }
}
