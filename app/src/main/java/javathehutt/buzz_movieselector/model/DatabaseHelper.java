package javathehutt.buzz_movieselector.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.User;

/**
 * Created by Frank on 3/7/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS users (username text not null , password text not null);";
    private static User currentUser;

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public boolean handleLogInRequest(String username, String password) {
        db = this.getReadableDatabase();
        String query = "select username, password from " + TABLE_NAME + " where username like \'" + username + "\'";
        Cursor cursor = db.rawQuery(query , null);

        if(cursor.moveToFirst()){
            if (username.equals(cursor.getString(0)) && password.equals(cursor.getString(1))) {
                currentUser = new RegUser(username, password);
                return true;
            }
        }
        return false;
    }

    public void addUser(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public User lastLogIn() {
        return currentUser;
    }

    public boolean isInSystem(String u) {
        db = this.getReadableDatabase();
        String query = "select username from " + TABLE_NAME + " where username like \'" + u + "\'";
        Cursor cursor = db.rawQuery(query , null);
        System.out.println(cursor.getCount() > 0);

        return cursor.getCount() > 0;
    }
}
