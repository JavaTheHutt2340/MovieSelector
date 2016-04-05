/**
 * Java controller for LoggedOut screen
 * @author Mohammed Saqib
 * @version 1.0
 * @date 02/15/16
 */
package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;

public class LoggedOut extends FragmentActivity
        implements FacebookFragment.OnFragmentInteractionListener {
    /**
     * creates and sets the screen
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
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
