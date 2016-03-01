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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        UserManager a = new UserMapManager();
        u = a.lastLogIn();
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

    /*
    * method for onClick for search movie
    * @param v the view
     */
    public void searchMovies(View v){
        Intent i = new Intent(this, MovieSearchActivity.class);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "mainMenuClosed closed");
    }
}
