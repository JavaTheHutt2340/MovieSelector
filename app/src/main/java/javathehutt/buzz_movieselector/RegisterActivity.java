package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;
import javathehutt.buzz_movieselector.model.DatabaseHelper;

/**
 * Activity for Registration Screen.
 *
 * @author Saqib Ali
 * @version 1.0
 */
public class RegisterActivity extends Activity {

    EditText etUsername, etPassword, etConfirmPassword;
    DatabaseHelper helper = new DatabaseHelper(this);

    /**
     * Initializes the RegisterActivity class
     * @param savedInstanceState Used so that you don't lose the activity when you back out of it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
    }

    /**
     * Checks if fields are valid when entering register information.
     * @return true if fields are valid or false if not.
     */
    private boolean validFields(){
        return etConfirmPassword.getText().toString().equals(etPassword.getText().toString())
                && etUsername.getText().toString().length() > 0
                && etPassword.getText().toString().length() > 0;
    }

    /**
     * ensures password contains no space characters
     * @return true if password allowed
     */
    private boolean validPassword(){
        return etPassword.getText().toString().indexOf(" ") == -1;
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
            } else if (validFields()){
                RegUser user = new RegUser(etUsername.getText().toString().trim(), etPassword.getText().toString());
                helper.addUser(user);
                CharSequence text = "User Successfully Registered!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                System.out.println(helper.handleLogInRequest(user.getUsername(), etPassword.getText().toString()));
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "RegisterActivity closed");
    }
}
