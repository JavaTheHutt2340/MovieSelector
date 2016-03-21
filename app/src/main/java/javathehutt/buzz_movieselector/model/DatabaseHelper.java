package javathehutt.buzz_movieselector.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.User;

/**
 * Created by Frank on 3/7/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static int currentCount = 0;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    public static final String KEY_ID = "_id";
    private static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_MAJOR = "major";
    private static final String COLUMN_ATTEMPTS = "numAttempts";
    private static final String COLUMN_BAN = "ban";
    private static final String COLUMN_LOCKED = "locked";
    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            KEY_ID          + " integer not null primary key autoincrement , " +
            COLUMN_USERNAME + " text not null , " +
            COLUMN_PASSWORD + " text not null , " +
            COLUMN_NAME     + " text not null , " +
            COLUMN_GENRE    + " int not null , " +
            COLUMN_LOCATION + " text not null , " +
            COLUMN_MAJOR    + " text not null, " +
            COLUMN_BAN      + " text not null, " +
            COLUMN_LOCKED   + " text not null, " +
            COLUMN_ATTEMPTS + " int not null );"; //9
    private static User currentUser;

    static SQLiteDatabase db;

    /**
     * Constructor
     * @param context   current context of program
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method to call when program is created
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    /**
     * Method for when database is to be upgraded
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    /**
     * Tests to see if the user is locked
     * @param u
     * @return
     */
    public boolean userLocked(User u) {
        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where username like \'" + u.getUsername() + "\'";
        Cursor cursor = db.rawQuery(query , null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(6) >= 3;
        }
        return false;
    }

    /**
     * Logs a user into database
     * @param username
     * @param password
     * @return true if successful
     */
    public int handleLogInRequest(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            currentUser = new AdminUser("admin", "admin");
            return 0;
        }
        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where username like \'" + username + "\'";
        Cursor cursor = db.rawQuery(query , null);
        if(cursor.moveToFirst()) {
            Log.i("login", "login = " + cursor.getString(7) + " | " + cursor.getString(8));
            if (password.equals(cursor.getString(2)) && cursor.getString(7).equals("false")
                    && cursor.getString(8).equals("false") && cursor.getInt(9) < RegUser.attemptsAllowed) {
                currentUser = new RegUser(username, password);
                currentUser.setRealName(cursor.getString(3));
                currentUser.setFavoriteGenre(cursor.getInt(4));
                currentUser.setLocation(cursor.getString(5));
                currentUser.setMajor(cursor.getString(6));
                currentUser.setFailedAttempts(0);
                db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_ATTEMPTS, 0);
                db.update(TABLE_NAME, values, "username like \'" + username + "\'", null);
                cursor.close();
                return 0;
            } else {
                db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_ATTEMPTS, cursor.getInt(9) + 1);
                if (cursor.getString(8).equals("false") && cursor.getInt(9) >= RegUser.attemptsAllowed) {
                    values.put(COLUMN_LOCKED, "true");
                }
                db.update(TABLE_NAME, values, "username like \'" + username + "\'", null);
                if(cursor.getString(7).equals("true")){
                    return 2;
                } else if (cursor.getString(8).equals("true")) {
                    return 3;
                }
            }
        }
        cursor.close();
        return 1;
    }

    /**
     * A way to add a user into the database
     * @param u
     */
    public void addUser(User u) {
        currentUser = u;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());
        values.put(COLUMN_NAME, u.getRealName());
        values.put(COLUMN_GENRE, u.getFavoriteGenreNum());
        values.put(COLUMN_LOCATION, u.getLocation());
        values.put(COLUMN_MAJOR, u.getMajor());
        values.put(COLUMN_BAN, "false");
        values.put(COLUMN_LOCKED, "false");
        values.put(COLUMN_ATTEMPTS, 0);
        values.put(KEY_ID, currentCount++);

        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.i("login", "adding user");
    }

    /**
     * A method to update the information of a user
     * @param u
     */
    public void updateUser(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, u.getRealName());
        values.put(COLUMN_GENRE, u.getFavoriteGenreNum());
        values.put(COLUMN_LOCATION, u.getLocation());
        values.put(COLUMN_MAJOR, u.getMajor());
        if (u instanceof RegUser) {
            RegUser temp = (RegUser) u;
            values.put(COLUMN_BAN, temp.getBanStatus() ? "true" : "false");
            values.put(COLUMN_LOCKED, temp.getLockStatus() ? "true" : "false");
            if (!temp.getLockStatus()) {
                values.put(COLUMN_ATTEMPTS, 0);
            }
        }

        db.update(TABLE_NAME, values, "username like \'" + u.getUsername() + "\'", null);
        db.close();
    }

    /**
     * returns the last user who logged in
     * @return User object
     */
    public User lastLogIn() {
        return currentUser;
    }

    /**
     * tells if a specific username is valid
     * @param u
     * @return
     */
    public boolean isInSystem(String u) {
        if (u.equals("admin")) {
            return true;
        }
        db = this.getReadableDatabase();
        String query = "select username from " + TABLE_NAME + " where username like \'" + u + "\'";
        Cursor cursor = db.rawQuery(query, null);
        System.out.println(cursor.getCount() > 0);

        return cursor.getCount() > 0;
    }

    /**
     * returns a list of all users
     * @return List of users
     */
    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            RegUser tempUser = new RegUser(cursor.getString(1), cursor.getString(2));
            tempUser.setRealName(cursor.getString(3));
            tempUser.setFavoriteGenre(cursor.getInt(4));
            tempUser.setLocation(cursor.getString(5));
            tempUser.setMajor(cursor.getString(6));
            if(cursor.getString(7).equals("true")) {
                tempUser.ban();
            }
            if(cursor.getString(8).equals("true")) {
                tempUser.lock();
            }
            //Log.i("login", tempUser.getBanStatus() + " " + tempUser.getUsername());
            list.add(tempUser);
            cursor.moveToNext();
        }
        return list;
    }
}
