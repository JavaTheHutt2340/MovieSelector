package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import javathehutt.buzz_movieselector.movie.Movie;
import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MovieViewActivity extends Activity {

    private RatingBar ratingBar;
    private MovieRatingManager manager;
    private Movie m;
    private TextView title;
    private TextView movieInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Bundle bundle = getIntent().getExtras();
        m = (Movie) bundle.getSerializable("object");
        title = (TextView) findViewById(R.id.Title);
        title.setText(m.getName());
        movieInfo = (TextView) findViewById(R.id.MovieInfo);
        movieInfo.setText("1. " + m.getYear() + "\n" + "2." + m.getCriticsRating() + "\n" + "3."
                + m.getCriticsScore() + "\n" + "4." + m.getGenre() + "\n" + "5." + m.getSynopsis()
                + "\n" + "6." + m.getUrl());
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        manager = new MovieMapRatingManager();
        ratingBar.setRating(manager.getRating(m));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(getApplicationContext(), "Your Selected Ratings  : "
                        + String.valueOf(rating), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ratingButtonClick (View v) {
        manager.addRatedMovie(m, ratingBar.getRating());
        finish();
    }

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
