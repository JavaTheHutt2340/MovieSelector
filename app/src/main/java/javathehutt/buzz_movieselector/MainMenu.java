package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.model.UserMapManager;

public class MainMenu extends AppCompatActivity {

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        UserManager a = new UserMapManager();
        u = a.lastLogIn();
    }
    public void logOut(View v) {
        u.logout();
        Intent i = new Intent(this, LoggedOut.class);
        startActivity(i);
        finish();
    }
}
