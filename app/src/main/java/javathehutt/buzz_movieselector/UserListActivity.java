package javathehutt.buzz_movieselector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.User;

import static android.R.layout.simple_list_item_1;

public class UserListActivity extends Activity {

    ListView userListView;
    static Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userListView = (ListView) findViewById(R.id.userListView);
        /*cursor = DatabaseHelper.getAllUsernames();
        String[] fromFieldNames = new String[] {DatabaseHelper.KEY_ID, DatabaseHelper.COLUMN_USERNAME};
        int[] toViewIDs = new int[] {simple_list_item_1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.id.userListView, cursor, fromFieldNames, toViewIDs);
        userListView.setAdapter(adapter);*/
        DatabaseHelper helper = new DatabaseHelper(this);
        final List<User> users = helper.getAllUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
                Intent intent = new Intent(getBaseContext(), DetailedUserView.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", users.get(pos));
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

}
