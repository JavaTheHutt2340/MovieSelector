package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javathehutt.buzz_movieselector.movie.RottenTomatoes;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        /*RottenTomatoesJSON r = new RottenTomatoesJSON(MainActivity.this);
        r.newMovieReleases(10);*/ //TODO this is how to use the class
    }

    /**
     * Method called when User
     *  wishes to register new account
     * @param v
     */
    public void registerButtonClick(View v) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }

    /**
     * Method to allow user to login
     * @param v
     */
    public void loginButtonClick(View v) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivityForResult(login, 1);
    }

}


