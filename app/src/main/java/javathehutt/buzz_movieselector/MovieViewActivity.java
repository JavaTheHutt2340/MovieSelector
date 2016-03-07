package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import javathehutt.buzz_movieselector.movie.Movie;
import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;

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

    public void ratingButtonClick (View v) {
        manager.addRatedMovie(m, ratingBar.getRating());
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
