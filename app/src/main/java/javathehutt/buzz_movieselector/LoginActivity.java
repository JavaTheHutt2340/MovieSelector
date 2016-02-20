package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    /**
     * Method called when User attempts to login
     * @param v
     */
    public void userLoginButtonClick(View v) {
        UserManager userManager = new UserMapManager();
        if (userManager.isInSystem(etUsername.getText().toString().toLowerCase())) {
            if (userManager.handleLogInRequest(etUsername.getText().toString().toLowerCase(), etPassword.getText().toString())) {
                Context context = getApplicationContext();
                CharSequence text = "Log In Successful!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Invalid Password!";
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
}
