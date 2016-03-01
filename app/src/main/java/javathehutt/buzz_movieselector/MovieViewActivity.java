package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;

public class MovieViewActivity extends Activity {

    private RatingBar ratingBar;
    private MovieRatingManager manager;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        manager = new MovieMapRatingManager();
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("movie");
        ratingBar.setRating(manager.getRating(title));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(getApplicationContext(), "Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ratingButtonClick (View v) {
        manager.addRatedMovie(title, ratingBar.getRating());
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
