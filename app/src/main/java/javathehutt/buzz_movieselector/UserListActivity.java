package javathehutt.buzz_movieselector;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import javathehutt.buzz_movieselector.model.DatabaseHelper;

import static android.R.layout.simple_list_item_1;

public class UserListActivity extends Activity {

    ListView userListView;
    static Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userListView = (ListView) findViewById(R.id.userListView);
        cursor = DatabaseHelper.getAllUsernames();
        String[] fromFieldNames = new String[] {DatabaseHelper.KEY_ID, DatabaseHelper.COLUMN_USERNAME};
        int[] toViewIDs = new int[] {simple_list_item_1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.id.userListView, cursor, fromFieldNames, toViewIDs);
        userListView.setAdapter(adapter);
    }

}
