package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.movie.Movie;
import javathehutt.buzz_movieselector.movie.MovieMapRatingManager;
import javathehutt.buzz_movieselector.movie.MovieRatingManager;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MovieViewActivity extends Activity {

    private RatingBar ratingBar;
    private MovieRatingManager manager;
    private DependencyContainer dc;
    private Movie m;
    private TextView title;
    private TextView movieInfo;
    private ShareButton share;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = new DependencyInjectionContainer(this);
        setContentView(R.layout.activity_movie_view);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        manager = dc.getMovieRatingDep();
        Bundle bundle = getIntent().getExtras();
        m = (Movie) bundle.getSerializable("object");
        title = (TextView) findViewById(R.id.Title);
        title.setText(m.getName());
        movieInfo = (TextView) findViewById(R.id.MovieInfo);
        movieInfo.setText("1. " + m.getYear() + "\n" + "2." + m.getCriticsRating() + "\n" + "3."
                + m.getCriticsScore() + "\n" + "\n" + "4." + m.getSynopsis()
                + "\n" + "5." + m.getAltUrl());
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //share button
        share = (ShareButton) findViewById(R.id.shareButton);
        share.setEnabled(FacebookFragment.getAt() != null);
        share.setVisibility(FacebookFragment.getAt() != null ? View.VISIBLE : View.GONE);
        if (m.getAltUrl() != null) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(m.getAltUrl()))
                    .build();
            share.setShareContent(content);
        } else {
            share.setEnabled(false);
            share.setVisibility(View.GONE);
        }


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
