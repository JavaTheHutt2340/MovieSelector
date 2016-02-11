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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button userLoginButton, cancelButton;
    EditText etUsername, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        userLoginButton = (Button) findViewById(R.id.userRegisterButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        userLoginButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.userRegisterButton:
                UserMapManager userManager = new UserMapManager();
                if (userManager.isInSystem(etUsername.getText().toString())) {
                    Context context = getApplicationContext();
                    CharSequence text = "User name is taken";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    if (!(etConfirmPassword.getText().toString().equals(etPassword.getText().toString()))) {
                        Context context = getApplicationContext();
                        CharSequence text = "Passwords don't match.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    RegUser user = new RegUser(etUsername.getText().toString(), etPassword.getText().toString());
                    userManager.addUser(user);
                    if (userManager.handleLogInRequest(etUsername.getText().toString(), etPassword.getText().toString())){
                        Context context = getApplicationContext();
                        CharSequence text = "Register Success!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(this, MainMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }
            case R.id.cancelButton:
                finish();
                break;
        }
    }
}
