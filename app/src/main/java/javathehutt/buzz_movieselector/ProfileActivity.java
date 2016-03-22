package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
 * @date 02/15/16
 */
public class ProfileActivity extends Activity {

    private User u;
    private DatabaseHelper helper;
    private DependencyContainer dc;

    /**
     * creates the activity, sets screen, and sets values in TextViews based on User attributes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dc = new DependencyInjectionContainer(this);
        helper = dc.getDatabaseDep();
        u = helper.lastLogIn();

        TextView realName = (TextView) findViewById(R.id.realNameEdit);
        realName.setText(u.getRealName());

        setTitle(u.getUsername() + "'s Profile");

        TextView location = (TextView) findViewById(R.id.locationProfileEdit);
        location.setText(u.getLocation());

        TextView major = (TextView) findViewById(R.id.major);
        major.setText(u.getMajor());

        Spinner genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new ArrayList<>(Arrays.asList(javathehutt.buzz_movieselector.model.User.getGenres())));
        genreSpinner.setAdapter(adapter);
        genreSpinner.setSelection(u.getFavoriteGenreNum());
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

        Spinner genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        u.setFavoriteGenre(genreSpinner.getSelectedItemPosition());

        TextView major = (TextView) findViewById(R.id.major);
        u.setMajor(major.getText().toString());

        helper.updateUser(u);

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
        CharSequence text = "Profile updated!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "profile activity closed");
    }
}
