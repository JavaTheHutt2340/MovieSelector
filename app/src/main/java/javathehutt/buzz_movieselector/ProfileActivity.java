package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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

    private Activity act;

    private final int PLACE_PICKER_REQUEST = 1;

    /**
     * creates the activity, sets screen, and
     * sets values in TextViews based on User attributes
     * @param savedInstanceState saved instance state
     */
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        helper = dc.getDatabaseDep();
        u = helper.lastLogIn();
        act = this;
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

    public void onLocationClick(View v) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        //This part of the code defines a rectangle defined in PlacePicker
        try {
            startActivityForResult(builder.build(act), PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            Log.e("Error", e.getStackTrace().toString());
        }
    }

    /**
     * onClick method for the Update Profile button.
     * Sets attributes in the User based on Strings
     * currently in the EditText widgets. Then
     * closes virtual keyboard and transfers focus to a
     * a dummy widget
     * @param v view
     */
    public final void updateProfileClick(View v) {
        final TextView realName = (TextView) findViewById(R.id.realNameEdit);
        u.setRealName(realName.getText().toString());

        final Button location = (Button) findViewById(R.id.locationProfileEdit);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getApplicationContext(), data);
                String toastMsg = String.format("Profile Updated!: %s", place.getName());
                Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_LONG).show();
                final Button location = (Button) findViewById(R.id.locationProfileEdit);
                location.setText(place.getName());
                u.setLocation(place.getName().toString());
                helper.updateUser(u);
            }
        }
    }

    /**
     * Represents onClick for cancel button.
     * @param v displays the cancel button
     */
    public final void mainMenuClick(View v) {
        finish();
    }

    @Override
    public final void onBackPressed() {
        finish();
    }
}
