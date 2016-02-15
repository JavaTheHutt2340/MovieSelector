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

        TextView realName = (TextView) findViewById(R.id.realNameEdit);
        realName.setText(u.getRealName());

        TextView usernameProfile = (TextView) findViewById(R.id.usernameProfile);
        usernameProfile.append(" " + u.getUsername());

        TextView location = (TextView) findViewById(R.id.locationProfileEdit);
        location.setText(u.getLocation());

        TextView genre = (TextView) findViewById(R.id.genreProfileEdit);
        genre.setText(u.getFavoriteGenre());
    }

    public void updateProfileClick(View v) {
        finish();
    }
}
