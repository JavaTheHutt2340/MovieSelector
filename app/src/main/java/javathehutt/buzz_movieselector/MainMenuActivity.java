package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.Activity;

import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MainMenuActivity extends Activity {

    private User u;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        UserManager a = new UserMapManager();
        u = a.lastLogIn();
        searchBar = (SearchView) findViewById(R.id.searchView);
        searchBar.setQueryHint("Search Movie");
    }

    /**
     * method for button to open profile
     *  from MainMenu
     * @param v
     */
    public void openProfileClick(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    /**
     * method for button to logOut
     *  from MainMenu
     * @param v
     */
    public void logOutClick(View v) {
        u.logout();

        Context context = getApplicationContext();
        CharSequence text = "Successfully Logged Out!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent i = new Intent(this, LoggedOut.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void searchMovies(View v){
        if (searchBar.isIconified() || searchBar.getQuery().length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter movie to search!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            String searchText = searchBar.getQuery().toString();
            Intent intent = new Intent(this, DisplayMoviesActivity.class);
            intent.putExtra("text", searchText);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        searchBar.setQuery("", false);
    }
}
