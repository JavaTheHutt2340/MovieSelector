package javathehutt.buzz_movieselector;

import android.os.Bundle;
import android.app.Activity;

import javathehutt.buzz_movieselector.model.User;

public class DetailedUserView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_user_view);
        Bundle bundle = getIntent().getExtras();
        User u = (User) bundle.getSerializable("user");
    }

}
