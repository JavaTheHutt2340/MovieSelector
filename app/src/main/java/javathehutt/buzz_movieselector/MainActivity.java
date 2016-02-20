package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import javathehutt.buzz_movieselector.movie.RottenTomatoes;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        SearchView searchBar = (SearchView) findViewById(R.id.searchView);
        searchBar.setQueryHint("Search Movie");
        /*RottenTomatoesJSON r = new RottenTomatoesJSON(MainActivity.this);
        r.newMovieReleases(10);*/ //TODO this is how to use the class
    }

    /**
     * Method called when User
     *  wishes to register new account
     * @param v
     */
    public void registerButtonClick(View v) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }

    /**
     * Method to allow user to login
     * @param v
     */
    public void loginButtonClick(View v) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivityForResult(login, 1);
    }

    public void searchMovies(View v){
        SearchView searchBar = (SearchView) findViewById(R.id.searchView);
        String searchText = searchBar.getQuery().toString();
        RottenTomatoesJSON RTJSON = new RottenTomatoesJSON(this);
        RTJSON.searchMovieByName(searchText,12);
    }
}


