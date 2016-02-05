package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;
import javathehutt.buzz_movieselector.model.welcome_screen_activity;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    Button userLoginButton, cancelButton;
    EditText etUsername, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        userLoginButton = (Button) findViewById(R.id.userLoginButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        userLoginButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.userLoginButton:
                Log.i("test", "login");
                UserManager userManager = new UserMapManager();
                Log.i("test", "login");
                if (userManager.handleLogInRequest(etUsername.getText().toString(), etPassword.getText().toString())) {
                    Log.i("test", "success");
                } else {
                    Log.i("test", "failure");
                }
                break;
            case R.id.cancelButton:
                Log.i("test", "cancel");
                finish();
                break;
        }
    }

}
