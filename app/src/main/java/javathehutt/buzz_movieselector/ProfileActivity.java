package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;

/**
 * Java controller for Profile screen
 * @author Frank Marzen
 * @version 1.0
 * @date 02/15/16
 */
public class ProfileActivity extends AppCompatActivity {

    private User u;

    /**
     * creates the activity, sets screen, and sets values in TextViews based on User attributes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserManager a = new UserMapManager();
        u = a.lastLogIn();

        TextView realName = (TextView) findViewById(R.id.realNameEdit);
        realName.setText(u.getRealName());

        TextView usernameProfile = (TextView) findViewById(R.id.usernameProfile);
        usernameProfile.append(" " + u.getUsername());

        TextView location = (TextView) findViewById(R.id.locationProfileEdit);
        location.setText(u.getLocation());

        TextView genre = (TextView) findViewById(R.id.genreProfileEdit);
        genre.setText(u.getFavoriteGenre());
    }

    /**
     * onClick method for the Update Profile button. Sets attributes in the User based on Strings
     * currently in the EditText widgets. Then closes virtual keyboard and transfers focus to a
     * a dummy widget
     * @param v view
     */
    public void updateProfileClick(View v) {
        TextView realName = (TextView) findViewById(R.id.realNameEdit);
        u.setRealName(realName.getText().toString());

        TextView location = (TextView) findViewById(R.id.locationProfileEdit);
        u.setLocation(location.getText().toString());

        TextView genre = (TextView) findViewById(R.id.genreProfileEdit);
        u.setFavoriteGenre(genre.getText().toString());

        //transfer focus to the dummy element to prevent cursor from going to EditText
        LinearLayout focusHolder = (LinearLayout)findViewById(R.id.focusHolder);
        focusHolder.requestFocus();

        //hide the keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
