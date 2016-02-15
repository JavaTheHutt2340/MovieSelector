package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void registerButtonClick(View v) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }

    public void loginButtonClick(View v) {
        Intent login = new Intent(this, loginActivity.class);
        startActivityForResult(login, 1);
    }

    //TODO add a logout button to logout from the main screen

}


