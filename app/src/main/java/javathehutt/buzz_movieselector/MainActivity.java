package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import javathehutt.buzz_movieselector.movie.RottenTomatoes;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_welcome_screen);

        //This code will create Facebook hash for android development
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "javathehutt.buzz_movieselector",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++" + e.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++" + e.toString());
        }

        /*RottenTomatoesJSON r = new RottenTomatoesJSON(MainActivity.this);
        r.newMovieReleases(10);*/ //TODO this is how to use the class
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        // Track metrics for app using Facebook tools
        AppEventsLogger.activateApp(this);
    }



    /**
     * Method called when User
     *  wishes to register new account
     * @param v
     */
    public void registerButtonClick(View v) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Method to allow user to login
     * @param v
     */
    public void loginButtonClick(View v) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivityForResult(login, 1);
    }


}


