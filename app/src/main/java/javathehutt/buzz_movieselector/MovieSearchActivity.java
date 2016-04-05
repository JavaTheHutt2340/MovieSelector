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

    private SearchView searchBar;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    public void dVDReleasesClick(View v) {
        final Intent i = new Intent(this, DisplayMoviesActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putInt("key", 1);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public void searchMoviesClick(View v) {
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
            bundle.putInt("key", 2);
            i.putExtras(bundle);
            startActivity(i);
        }

    }

    /**
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public void inTheatreClick(View v) {
        final Intent i = new Intent(this, DisplayMoviesActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putInt("key", 3);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * onclick for recommend button
     * @param v the view
     */
    public void recommendClick(View v) {
        final Intent i = new Intent(this, DisplayMoviesActivity.class);
        final Bundle bundle = new Bundle();
        final User u = helper.lastLogIn();
        bundle.putInt("key", 4);
        final ArrayList<String> list = new ArrayList<>();
        list.add(u.getFavoriteGenre());
        list.add(u.getMajor());
        bundle.putStringArrayList("recommend", list);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchBar.setQuery("", false);
    }
}
