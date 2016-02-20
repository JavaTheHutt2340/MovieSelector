/**
 * Java controller for LoggedOut screen
 * @author Mohammed Saqib
 * @version 1.0
 * @date 02/15/16
 */
package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class LoggedOut extends Activity {
    /**
     * creates and sets the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
