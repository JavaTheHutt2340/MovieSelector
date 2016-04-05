package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import javathehutt.buzz_movieselector.movie.Movie;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MovieViewActivity extends Activity {

    private RatingBar ratingBar;
    //private MovieRatingManager manager;
    private Movie m;
    private SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        sharedPref = this.getPreferences(MODE_PRIVATE);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //manager = dc.getMovieRatingDep();
        final Bundle bundle = getIntent().getExtras();
        m = (Movie) bundle.getSerializable("object");
        TextView title = (TextView) findViewById(R.id.Title);
        title.setText(m.getName());
        TextView movieInfo = (TextView) findViewById(R.id.MovieInfo);
        movieInfo.setText(String.format("Year: %d\n\nCritics Rating: %s\n\n"
                + "Critics Score: %s\n\nSynopsis: %s", m.getYear()
                , m.getCriticsRating(), m.getCriticsScore(), m.getSynopsis()));
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //share button
        ShareButton share = (ShareButton) findViewById(R.id.shareButton);
        share.setEnabled(FacebookFragment.getAt() != null);
        share.setVisibility(FacebookFragment.getAt()
                != null ? View.VISIBLE : View.GONE);
        if (m.getAltUrl() != null) {
            final ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(m.getAltUrl()))
                    .build();
            share.setShareContent(content);
        } else {
            share.setEnabled(false);
            share.setVisibility(View.GONE);
        }

        final float rating = sharedPref.getFloat(m.getApiUrl(), 0);

        //manager = new MovieMapRatingManager();
        //ratingBar.setRating(manager.getRating(m));
        ratingBar.setRating(rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar
                .OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar mratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(getApplicationContext(),
                        "Your Selected Ratings  : "
                        + rating, Toast
                                .LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method for when rating a movie is done
     * @param v the view
     */
    public void ratingButtonClick(View v) {
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(m.getApiUrl(), ratingBar.getRating());
        editor.apply();

        //manager.addRatedMovie(m, ratingBar.getRating());
        finish();
    }

    /**
     * Method for when looking up similar movies
     * @param v the view
     */
    public void similarMovies(View v) {
        new RottenTomatoesJSON(getApplicationContext()).similarMovies(m);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
