package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.User;

/**
 * Java controller for Profile screen
 * @author Frank Marzen
 * @version 1.0
 */
public class ProfileActivity extends Activity {

    /**
     * the current user
     */
    private User u;
    /**
     * the database helper
     */
    private DatabaseHelper helper;

    /**
     * creates the activity, sets screen, and
     * sets values in TextViews based on User attributes
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        helper = dc.getDatabaseDep();
        u = helper.lastLogIn();

        final TextView realName = (TextView) findViewById(R.id.realNameEdit);
        realName.setText(u.getRealName());

        setTitle(u.getUsername() + "'s Profile");

        final TextView location = (TextView) findViewById(R.id.locationProfileEdit);
        location.setText(u.getLocation());

        final TextView major = (TextView) findViewById(R.id.major);
        major.setText(u.getMajor());

        final Spinner genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(Arrays.asList(javathehutt
                        .buzz_movieselector.model.User.getGenres())));
        genreSpinner.setAdapter(adapter);
        genreSpinner.setSelection(u.getFavoriteGenreNum());
    }

    /**
     * onClick method for the Update Profile button.
     * Sets attributes in the User based on Strings
     * currently in the EditText widgets. Then
     * closes virtual keyboard and transfers focus to a
     * a dummy widget
     * @param v view
     */
    public void updateProfileClick(View v) {
        final TextView realName = (TextView) findViewById(R.id.realNameEdit);
        u.setRealName(realName.getText().toString());

        final TextView location = (TextView) findViewById(R.id.locationProfileEdit);
        u.setLocation(location.getText().toString());

        final Spinner genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        u.setFavoriteGenre(genreSpinner.getSelectedItemPosition());

        final TextView major = (TextView) findViewById(R.id.major);
        u.setMajor(major.getText().toString());

        helper.updateUser(u);

        //transfer focus to the dummy element
        // to prevent cursor from going to EditText
        final LinearLayout focusHolder =
                (LinearLayout) findViewById(R.id.focusHolder);
        focusHolder.requestFocus();

        //hide the keyboard
        final InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        final CharSequence text = "Profile updated!";
        final int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    /**
     * Represents onClick for cancel button.
     * @param v displays the cancel button
     */
    public void mainMenuClick(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
