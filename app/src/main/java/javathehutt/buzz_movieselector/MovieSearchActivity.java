package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.User;

public class MovieSearchActivity extends Activity {
    public static final int NEWDVD = 1;
    public static final int BYNAME = 2;
    public static final int INTHEATRE = 3;
    public static final int RECOMMEND = 4;
    /**
     * Utility String for bundling with searches
     */
    private static final String KEY_STRING = "key";

    /**
     * search movie widget
     */
    private SearchView searchBar;
    /**
     * database interfacer
     */
    private DatabaseHelper helper;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        helper = dc.getDatabaseDep();
        setContentView(R.layout.activity_movie_search);
        searchBar = (SearchView) findViewById(R.id.searchView);
        searchBar.setQueryHint("Search Movie");
    }


    /**
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public final void dVDReleasesClick(View v) {
        final Intent i = new Intent(this, DisplayMoviesActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putInt(KEY_STRING, NEWDVD);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public final void searchMoviesClick(View v) {
        if (searchBar.isIconified() || searchBar.getQuery().length() == 0) {
            final Context context = getApplicationContext();
            final CharSequence text = "Please enter movie to search!";
            final int duration = Toast.LENGTH_LONG;
            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            final String searchText = searchBar.getQuery().toString();
            final Intent i = new Intent(this, DisplayMoviesActivity.class);
            i.putExtra("text", searchText);
            final Bundle bundle = new Bundle();
            bundle.putInt(KEY_STRING, BYNAME);
            i.putExtras(bundle);
            startActivity(i);
        }

    }

    /**
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public final void inTheatreClick(View v) {
        final Intent i = new Intent(this, DisplayMoviesActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putInt(KEY_STRING, INTHEATRE);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * onclick for recommend button
     * @param v the view
     */
    public final void recommendClick(View v) {
        final Intent i = new Intent(this, DisplayMoviesActivity.class);
        final Bundle bundle = new Bundle();
        final User u = helper.lastLogIn();
        bundle.putInt(KEY_STRING, RECOMMEND);
        final ArrayList<String> list = new ArrayList<>();
        list.add(u.getFavoriteGenre());
        list.add(u.getMajor());
        bundle.putStringArrayList("recommend", list);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public final void onResume() {
        super.onResume();
        searchBar.setQuery("", false);
    }
}
