/**
 * Java controller for LoggedOut screen
 * @author Mohammed Saqib
 * @version 1.0
 * @date 02/15/16
 */
package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;

import com.facebook.FacebookSdk;

public class LoggedOut extends FragmentActivity implements FacebookFragment.OnFragmentInteractionListener{
    /**
     * creates and sets the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_logged_out);
    }

    /**
     * Method for button "Return Home"
     * Returns user to Home screen.
     * @param v current view
     */
    public void returnHome(View v) {
        finish();
    }

    /**
     * Method for button
     * @param v current view
     */
    public void returnLogIn(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }
}
