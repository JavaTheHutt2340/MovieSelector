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
import javathehutt.buzz_movieselector.model.welcome_screen_activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager userManager = new UserMapManager();
        userManager.addUser(new RegUser("user", "pass"));
        Intent welcome = new Intent(this, welcome_screen_activity.class);
        startActivity(welcome);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.registerButton: // place buttton id here
                //TODO place action to occur when button pressed here
                break;
        }
    }

    //TODO add a logout button to logout from the main screen
}
