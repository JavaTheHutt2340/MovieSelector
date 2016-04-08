package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.LoginResult;

public class LoginActivity extends FragmentActivity
        implements FacebookFragment.OnFragmentInteractionListener {
    /**
     * the username text field
     */
    private EditText etUsername;
    /**
     * password text field
     */
    private EditText etPassword;
    /**
     * the database interfacer
     */
    private DatabaseHelper helper;


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(this);
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        helper = dc.getDatabaseDep();
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    /**
     * Method called when User attempts to login
     * @param v the view
     */
    public final void userLoginButtonClick(View v) {
        if (helper.isInSystem(etUsername.getText().toString().toLowerCase())) {
            final int value = helper.handleLogInRequest(etUsername.getText()
                    .toString().toLowerCase(), etPassword.getText().toString());
            if (value == LoginResult.SUCCESS.ordinal()) {
                final Context context = getApplicationContext();
                final CharSequence text = "Log In Successful!";
                final int duration = Toast.LENGTH_SHORT;
                final Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                final Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                final Context context = getApplicationContext();
                CharSequence text = "Invalid Password!";
                if (value == LoginResult.BANNED.ordinal()) {
                    text = "This account is banned!";
                } else if (value == LoginResult.LOCKED.ordinal()) {
                    text = "This account is locked!";
                }
                final int duration = Toast.LENGTH_SHORT;
                final Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } else {
            final Context context = getApplicationContext();
            final CharSequence text = "User Does Not Exist!";
            final int duration = Toast.LENGTH_SHORT;
            final Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Method called when user no
     * longer wishes to log in
     * @param v the view
     */
    public final void cancelButtonClick(View v) {
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
