package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javathehutt.buzz_movieselector.model.User;
import javathehutt.buzz_movieselector.model.UserManager;
import javathehutt.buzz_movieselector.model.UserMapManager;

public class ProfileActivity extends AppCompatActivity {

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserManager a = new UserMapManager();
        u = a.lastLogIn();
        TextView usernameEdit = (TextView) findViewById(R.id.usernameProfileEdit);
        usernameEdit.setText(" " + u.getUsername());
    }
}
