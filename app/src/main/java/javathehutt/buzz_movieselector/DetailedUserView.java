package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.RegUser;

public class DetailedUserView extends Activity {

    /**
     * the current user
     */
    private RegUser u;
    /**
     * button to ban/unban user
     */
    private Button banButton;
    /**
     * button to lock or unlock user
     */
    private Button lockButton;
    /**
     * the database interfacer
     */
    private DatabaseHelper db;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_user_view);
        final Bundle bundle = getIntent().getBundleExtra("bundle");
        u = (RegUser) bundle.getSerializable("user");
        banButton = (Button) findViewById(R.id.banButton);
        lockButton = (Button) findViewById(R.id.lockButton);
        if (u.getLockStatus()) {
            lockButton.setText(R.string.unlock);
        } else {
            lockButton.setText(R.string.Lock);
        }
        if (u.getBanStatus()) {
            banButton.setText(R.string.unBan);
        } else {
            banButton.setText(R.string.Ban);
        }
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        db = dc.getDatabaseDep();
    }

    /**
     * Method to allow admin to lock a specific user
     * @param view the view
     */
    public final void lockButtonClick(View view) {
        if (u.getLockStatus()) {
            u.unlock();
            db.updateUser(u);
            lockButton.setText(R.string.Lock);
        } else {
            u.lock();
            db.updateUser(u);
            lockButton.setText(R.string.unlock);
        }
    }

    /**
     * Method to ban a specific user
     * @param view the view
     */
    public final void banButtonClick(View view) {
        if (u.getBanStatus()) {
            u.unBan();
            db.updateUser(u);
            banButton.setText(R.string.Ban);
        } else {
            u.ban();
            db.updateUser(u);
            banButton.setText(R.string.unBan);
        }
    }

    @Override
    public final void onBackPressed() {
        finish();
    }
}
