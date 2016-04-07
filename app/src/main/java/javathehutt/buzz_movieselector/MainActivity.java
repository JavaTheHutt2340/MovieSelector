package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends FragmentActivity
        implements FacebookFragment.OnFragmentInteractionListener {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_welcome_screen);
        //This code will create Facebook hash for android development
        /*try {
            final PackageInfo info = getPackageManager().getPackageInfo(
                    "javathehutt.buzz_movieselector",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++"
                        + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++"
                    + e.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", "++++++++++++++++++++++++++++++++++++++"
                    + e.toString());
        }*/
    }


    @Override
    protected final void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        // Track metrics for app using Facebook tools
        AppEventsLogger.activateApp(this);
    }



    /**
     * Method called when User
     *  wishes to register new account
     * @param v the view
     */
    public final void registerButtonClick(View v) {
        final Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }



    /**
     * Method to allow user to login
     * @param v the view
     */
    public final void loginButtonClick(View v) {
        final Intent login = new Intent(this, LoginActivity.class);
        startActivityForResult(login, 1);
    }

    @Override
    public void onFragmentInteraction(Uri urit) {
    }
}


