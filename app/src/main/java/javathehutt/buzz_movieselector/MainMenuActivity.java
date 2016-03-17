package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.User;

public class MainMenuActivity extends Activity {

    private User u;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        u = db.lastLogIn();
        System.out.println(u.getUsername());
        Button userListButton = (Button) findViewById(R.id.button3);
        userListButton.setEnabled(u.isAdmin());
        userListButton.setVisibility(u.isAdmin() ? View.VISIBLE : View.GONE);
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

    public void userListClick(View v){
        Intent i = new Intent(this, UserListActivity.class);
        startActivity(i);
    }
}
