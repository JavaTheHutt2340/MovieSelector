package javathehutt.buzz_movieselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.User;

public class UserListActivity extends Activity {

    private final List<User> users = new ArrayList<>();
    private DatabaseHelper helper;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        final DependencyContainer dc = new DependencyInjectionContainer(this);
        final ListView userListView = (ListView) findViewById(R.id.userListView);
        helper = dc.getDatabaseDep();
        users.addAll(helper.getAllUsers());
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View view, int pos, long arg3) {
                final Intent intent = new Intent(getBaseContext(),
                        DetailedUserView.class);
                final Bundle bundle = new Bundle();
                bundle.putSerializable("user", users.get(pos));
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(helper.getAllUsers());
    }

}
