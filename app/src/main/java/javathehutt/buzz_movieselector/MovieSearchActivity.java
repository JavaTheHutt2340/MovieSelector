package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MovieSearchActivity extends Activity {

    private SearchView searchBar;
    private RottenTomatoesJSON json;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        json = new RottenTomatoesJSON(MovieSearchActivity.this);
        searchBar = (SearchView) findViewById(R.id.searchView);
        searchBar.setQueryHint("Search Movie");
    }


    /*
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public void dVDReleasesClick(View v) {
        Intent i = new Intent(this, DisplayMoviesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("key", 1);
        i.putExtras(bundle);
        startActivity(i);
    }

    /*
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public void searchMoviesClick(View v) {
        if (searchBar.isIconified() || searchBar.getQuery().length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter movie to search!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            String searchText = searchBar.getQuery().toString();
            Intent i = new Intent(this, DisplayMoviesActivity.class);
            i.putExtra("text", searchText);
            Bundle bundle = new Bundle();
            bundle.putInt("key", 2);
            i.putExtras(bundle);
            startActivity(i);
        }

    }

    /*
    * creates the starts the Display Movie activity
    * @param v the view
     */
    public void inTheatreClick(View v) {
        Intent i = new Intent(this, DisplayMoviesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("key", 3);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void recommendClick(View v) {
        Intent i = new Intent(this, DisplayMoviesActivity.class);
        Bundle bundle = new Bundle();
        User u = helper.lastLogIn();
        bundle.putInt("key", 4);
        ArrayList<String> list = new ArrayList<>();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "MovieSearchActivity closed");
    }
}
