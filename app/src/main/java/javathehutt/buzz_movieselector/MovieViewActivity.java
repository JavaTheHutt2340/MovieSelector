package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import javathehutt.buzz_movieselector.movie.Movie;
import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MovieViewActivity extends Activity {

    private RatingBar ratingBar;
    private MovieRatingManager manager;
    private Movie m;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        manager = new MovieMapRatingManager();
        Bundle bundle = getIntent().getExtras();
        m = (Movie) bundle.getSerializable("object");
        ratingBar.setRating(manager.getRating(m));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(getApplicationContext(), "Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method for when rating a movie is done
     * @param v
     */
    public void ratingButtonClick (View v) {
        manager.addRatedMovie(m, ratingBar.getRating());
        finish();
    }

    /**
     * Method for when looking up similar movies
     * @param v
     */
    public void similarMovies (View v) {
        new RottenTomatoesJSON(getApplicationContext()).similarMovies(m);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "MovieViewActivity closed");
    }
}
