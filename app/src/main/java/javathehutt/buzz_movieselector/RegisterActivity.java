package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;
import javathehutt.buzz_movieselector.model.User;


public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etConfirmPassword;
    UserManager userManager = new UserMapManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
    }
    public boolean validFields(){
        return etConfirmPassword.getText().toString().equals(etPassword.getText().toString())
                && etUsername.getText().toString().length() > 0
                && etPassword.getText().toString().length() > 0;
    }
    public void registerClick(View v) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        if (userManager.isInSystem(etUsername.getText().toString())) {
            CharSequence text = "Username is taken.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            if (validFields()){
                RegUser user = new RegUser(etUsername.getText().toString(), etPassword.getText().toString());
                userManager.addUser(user);
                CharSequence text = "Register Success!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                userManager.handleLogInRequest(user.getUsername(), etPassword.getText().toString());
                Intent intent = new Intent(this, MainMenu.class);
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

    public void cancelClick(View v) {
        finish();
    }
}
