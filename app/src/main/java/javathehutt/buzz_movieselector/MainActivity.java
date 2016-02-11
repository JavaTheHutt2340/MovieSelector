package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        UserManager userManager = new UserMapManager();
        userManager.addUser(new RegUser("user", "pass"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginButton:
                Intent login = new Intent(this, loginActivity.class);
                startActivity(login);
                //TODO
                //finish() if logged in
                //else do not finish
                break;
            case R.id.registerButton:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }

    //TODO add a logout button to logout from the main screen
}
