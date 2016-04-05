package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.RegUser;

/**
 * Activity for Registration Screen.
 *
 * @author Saqib Ali
 * @version 1.0
 */
public class RegisterActivity extends Activity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private DatabaseHelper helper;

    /**
     * Initializes the RegisterActivity class
     * @param savedInstanceState stores saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DependencyContainer dc = new DependencyInjectionContainer(this);
        helper = dc.getDatabaseDep();
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)
                findViewById(R.id.etConfirmPassword);
    }

    /**
     * Checks if fields are valid when entering register information.
     * @return true if fields are valid or false if not.
     */
    private boolean validFields() {
        return etConfirmPassword.getText().toString()
                .equals(etPassword.getText().toString())
                && etUsername.getText().toString().trim().length() > 0
                && etPassword.getText().toString().length() > 0
                && !etPassword.getText().toString().contains(" ");
    }

    /**
     * ensures password contains no space characters
     * @return true if password allowed
     */
    private boolean validPassword() {
        return !etPassword
                .getText().toString().contains(" ");
    }
    /**
     * Represents onClick for register button.
     * @param v displays the register button
     */
    public void registerButtonClick(View v) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        if (helper.isInSystem(etUsername.getText().toString())) {
            CharSequence text = "Username is taken.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            if (!validPassword()) {
                CharSequence text = "Password cannot contain spaces.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (validFields()) {
                RegUser user = new RegUser(etUsername.getText()
                                .toString().trim(), etPassword.getText()
                                .toString());
                helper.addUser(user);
                CharSequence text = "User Successfully Registered!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else if (!etPassword.getText().toString()
                    .equals(etConfirmPassword.getText().toString())) {
                CharSequence text = "Passwords don't match.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                CharSequence text = "Please fill out all fields.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }
    }

    /**
     * Represents onClick for cancel button.
     * @param v displays the cancel button
     */
    public void cancelClick(View v) {
        finish();
    }
}
