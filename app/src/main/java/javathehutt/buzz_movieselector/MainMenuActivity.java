package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.User;

public class MainMenuActivity extends Activity {

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyContainer dc = new DependencyInjectionContainer(this);
        DatabaseHelper db = dc.getDatabaseDep();
        setContentView(R.layout.activity_main_menu);
        u = db.lastLogIn();
        final Button userListButton = (Button) findViewById(R.id.button3);
        userListButton.setEnabled(u.isAdmin());
        userListButton.setVisibility(u.isAdmin() ? View.VISIBLE : View.GONE);
    }

    /**
     * method for button to open profile
     *  from MainMenu
     * @param v the view
     */
    public void openProfileClick(View v) {
        final Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    /**
     * method for button to logOut
     *  from MainMenu
     * @param v the view
     */
    public void logOutClick(View v) {
        u.logout();
        FacebookFragment.clear();

        final Context context = getApplicationContext();
        final CharSequence text = "Successfully Logged Out!";
        final int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        final Intent i = new Intent(this, LoggedOut.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    /**
    * method for onClick for search movie
    * @param v the view
     */
    public void searchMovies(View v) {
        final Intent i = new Intent(this, MovieSearchActivity.class);
        startActivity(i);
    }

    /**
     * on click for user list button
     * @param v the view
     */
    public void userListClick(View v) {
        final Intent i = new Intent(this, UserListActivity.class);
        startActivity(i);
    }
}
