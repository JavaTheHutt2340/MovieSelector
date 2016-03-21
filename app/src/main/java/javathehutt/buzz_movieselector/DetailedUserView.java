package javathehutt.buzz_movieselector;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.User;

public class DetailedUserView extends Activity {

    RegUser u;
    Button banButton, lockButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_user_view);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        u = (RegUser) bundle.getSerializable("user");
        banButton = (Button) findViewById(R.id.banButton);
        lockButton = (Button) findViewById(R.id.lockButton);
        if (u.getLockStatus()) {
            lockButton.setText("unlock account");
        } else {
            lockButton.setText("lock account");
        }
        if (u.getBanStatus()) {
            banButton.setText("unBan account");
        } else {
            banButton.setText("ban account");
        }
        db = new DatabaseHelper(this);
    }

    /**
     * Method to allow admin to lock a specific user
     * @param view
     */
    public void lockButtonClick(View view) {
        if (u.getLockStatus()) {
            u.unlock();
            db.updateUser(u);
            lockButton.setText("lock account");
        } else {
            u.lock();
            db.updateUser(u);
            lockButton.setText("unlock account");
        }
    }

    /**
     * Method to ban a specific user
     * @param view
     */
    public void banButtonClick(View view) {
        if (u.getBanStatus()) {
            u.unBan();
            db.updateUser(u);
            banButton.setText("ban account");
        } else {
            u.ban();
            db.updateUser(u);
            banButton.setText("unBan account");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
