package javathehutt.buzz_movieselector;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import javathehutt.buzz_movieselector.model.User;

public class DetailedUserView extends Activity {

    User u;
    Button banButton, lockButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_user_view);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        User u = (User) bundle.getSerializable("user");
        banButton = (Button) findViewById(R.id.banButton);
        lockButton = (Button) findViewById(R.id.lockButton);
        if (u.getLockStatus()) {
            lockButton.setText("unlock account");
        } else {
            lockButton.setText("lock account");
        }
    }

    public void lockButtonClick(View view) {
        if (u.getLockStatus()) {
            lockButton.setText("unlock account");
        } else {
            lockButton.setText("lock account");
        }
    }

    public void banButtonClick(View view) {

    }
}
