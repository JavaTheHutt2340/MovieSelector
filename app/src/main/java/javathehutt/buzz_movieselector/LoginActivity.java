package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import javathehutt.buzz_movieselector.model.DatabaseHelper;

public class LoginActivity extends FragmentActivity implements FacebookFragment.OnFragmentInteractionListener {
    CallbackManager callbackManager;
    EditText etUsername, etPassword;
    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(this);

        callbackManager = CallbackManager.Factory.create();
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    /**
     * Method called when User attempts to login
     * @param v
     */
    public void userLoginButtonClick(View v) {
        if (helper.isInSystem(etUsername.getText().toString().toLowerCase())) {
            int value = helper.handleLogInRequest(etUsername.getText().toString().toLowerCase(), etPassword.getText().toString());
            if (value == 0) {
                Context context = getApplicationContext();
                CharSequence text = "Log In Successful!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                //Log.i("login", "password =" + etPassword.getText().toString() + "end");
                Context context = getApplicationContext();
                CharSequence text = "Invalid Password!";
                if (value == 2) {
                    text = "this account is banned";
                } else if (value == 3) {
                    text = "this account is locked";
                }
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } else {
            Context context = getApplicationContext();
            CharSequence text = "User Does Not Exist!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Method called when user no
     * longer wishes to log in
     * @param v
     */
    public void cancelButtonClick(View v) {
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
            callbackManager.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "LoginActivity closed");
    }
}
