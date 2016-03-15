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
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_MAJOR = "major";
    private static final String COLUMN_ATTEMPTS = "numAttempts";
    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_USERNAME + " text not null , " +
            COLUMN_PASSWORD + " text not null , " +
            COLUMN_NAME     + " text not null , " +
            COLUMN_GENRE    + " int not null , " +
            COLUMN_LOCATION + " text not null , " +
            COLUMN_MAJOR    + " text not null, " +
            COLUMN_ATTEMPTS + " int not null );";
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

    public boolean userLocked(User u) {
        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where username like \'" + u.getUsername() + "\'";
        Cursor cursor = db.rawQuery(query , null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(6) >= 3;
        }
        return false;
    }

    public boolean handleLogInRequest(String username, String password) {
        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where username like \'" + username + "\'";
        Cursor cursor = db.rawQuery(query , null);

        if(cursor.moveToFirst()){
            if (password.equals(cursor.getString(1)) && cursor.getInt(6) < 3) {
                currentUser = new RegUser(username, password);
                currentUser.setRealName(cursor.getString(2));
                currentUser.setFavoriteGenre(cursor.getInt(3));
                currentUser.setLocation(cursor.getString(4));
                currentUser.setMajor(cursor.getString(5));
                currentUser.setFailedAttempts(cursor.getInt(6));

                db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_ATTEMPTS, 0);
                db.update(TABLE_NAME, values, "username like \'" + username + "\'", null);

                cursor.close();
                return true;
            } else {
                db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_ATTEMPTS, cursor.getInt(6) + 1);
                db.update(TABLE_NAME, values, "username like \'" + username + "\'", null);
            }
        }
        cursor.close();
        return false;
    }

    public void addUser(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());
        values.put(COLUMN_NAME, u.getRealName());
        values.put(COLUMN_GENRE, u.getFavoriteGenreNum());
        values.put(COLUMN_LOCATION, u.getLocation());
        values.put(COLUMN_MAJOR, u.getMajor());
        values.put(COLUMN_ATTEMPTS, u.getLogAttempts());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateUser(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, u.getRealName());
        values.put(COLUMN_GENRE, u.getFavoriteGenreNum());
        values.put(COLUMN_LOCATION, u.getLocation());
        values.put(COLUMN_MAJOR, u.getMajor());

        db.update(TABLE_NAME, values, "username like \'" + u.getUsername() + "\'", null);
        db.close();
    }

    public User lastLogIn() {
        return currentUser;
    }

    public boolean isInSystem(String u) {
        db = this.getReadableDatabase();
        String query = "select username from " + TABLE_NAME + " where username like \'" + u + "\'";
        Cursor cursor = db.rawQuery(query, null);
        System.out.println(cursor.getCount() > 0);

        return cursor.getCount() > 0;
    }
}
