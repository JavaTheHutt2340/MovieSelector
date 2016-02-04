package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import javathehutt.buzz_movieselector.model.welcome_screen_activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        Intent welcome = new Intent(this, welcome_screen_activity.class);
        startActivity(welcome);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginButton:
                Intent login = new Intent(this, loginActivity.class);
                Log.i("test", "here");
                startActivity(login);
                break;
            case R.id.registerButton:
                break;
        }
    }
}
