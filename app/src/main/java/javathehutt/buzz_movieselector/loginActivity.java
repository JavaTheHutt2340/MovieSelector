package javathehutt.buzz_movieselector;

import android.content.Context;
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
import android.widget.Toast;

import javathehutt.buzz_movieselector.model.AuthenticationManager;
import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;

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
                UserManager userManager = new UserMapManager();
                //TODO add an exception here for when they enter an invalid username
                if (userManager.isInSystem(etUsername.getText().toString())) {
                    if (userManager.handleLogInRequest(etUsername.getText().toString(), etPassword.getText().toString())) {
                        Context context = getApplicationContext();
                        CharSequence text = "Log In Success!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(this, MainMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                    CharSequence text = "Not a Valid Username";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
            case R.id.cancelButton:
                finish();
                break;
        }
    }

}
