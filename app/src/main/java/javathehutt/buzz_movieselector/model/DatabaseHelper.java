package javathehutt.buzz_movieselector.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javathehutt.buzz_movieselector.R;

/**
 * Created by Frank on 3/7/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * int representing column of username
     */
    private static final int USERNAME_IND = 1;
    /**
     * int representing column of passwords
     */
    private static final int PASSWORD_IND = 2;
    /**
     * int representing column of real name
     */
    private static final int REAL_NAME_IND = 3;
    /**
     * int representing column containing fav genre
     */
    private static final int GENRE_IND = 4;
    /**
     * int representing location column
     */
    private static final int LOCATION_IND = 5;
    /**
     * int representing major column
     */
    private static final int MAJOR_IND = 6;
    /**
     * int representing column containing ban info
     */
    private static final int BAN_IND = 7;
    /**
     * int representing column containing locked info
     */
    private static final int LOCKED_INDEX = 8;
    /**
     * int representing attempts column ind
     */
    private static final int ATTEMPT_IND = 9;

    /**
     * Utility String for admin user check
     */
    private static final String ADMIN = "admin";

    /**
     * Utility String for false
     */
    private static final String FALSE_STRING = "false";

    /**
     * Utility String for true
     */
    private static final String TRUE_STRING = "true";

    /**
     * Represents number of users in database
     */
    private static int currentCount = 0;
    /**
     * text not null String
     */
    private static final String TEXTNOTNULL = " text not null , ";
    /**
     * the database version
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * the database name
     */
    private static final String DATABASE_NAME = "users.db";
    /**
     * the id of the database
     */
    public static final String KEY_ID = "_id";
    /**
     * the table name
     */
    private static final String TABLE_NAME = "users";
    /**
     * username column
     */
    public static final String COLUMN_USERNAME = "username";
    /**
     * password column
     */
    private static final String COLUMN_PASSWORD = "password";
    /**
     * name column
     */
    private static final String COLUMN_NAME = "name";
    /**
     * genre column
     */
    private static final String COLUMN_GENRE = "genre";
    /**
     * location column
     */
    private static final String COLUMN_LOCATION = "location";
    /**
     * major column
     */
    private static final String COLUMN_MAJOR = "major";
    /**
     * number of failed login attempts column
     */
    private static final String COLUMN_ATTEMPTS = "numAttempts";
    /**
     * ban column
     */
    private static final String COLUMN_BAN = "ban";
    /**
     * locked column
     */
    private static final String COLUMN_LOCKED = "locked";
    /**
     * String to create the table
     */
    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY_ID          + " integer not null primary key autoincrement , "
            + COLUMN_USERNAME + TEXTNOTNULL
            + COLUMN_PASSWORD + TEXTNOTNULL
            + COLUMN_NAME     + TEXTNOTNULL
            + COLUMN_GENRE    + " int not null , "
            + COLUMN_LOCATION + TEXTNOTNULL
            + COLUMN_MAJOR    + TEXTNOTNULL
            + COLUMN_BAN      + TEXTNOTNULL
            + COLUMN_LOCKED   + TEXTNOTNULL
            + COLUMN_ATTEMPTS + " int not null );";
    /**
     * the current user
     */
    private static User currentUser;
    /**
     * the context of the current activity
     */
    private Context c;
    /**
     * the database
     */
    private static SQLiteDatabase db;

    /**
     * Constructor
     * @param context   current context of program
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    /**
     * Method to call when program is created
     * @param database the database
     */
    @Override
    public final void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
        DatabaseHelper.db = database;
    }

    /**
     * Method for when database is to be upgraded
     * @param database the database
     * @param oldVersion old version number
     * @param newVersion new version number
     */
    @Override
    public final void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        final String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        database.execSQL(query);
        this.onCreate(database);
    }

    /**
     * Logs a user into database
     * @param username the username
     * @param password the password
     * @return 0 if successful
     */
    public final int handleLogInRequest(String username, String password) {
        if (ADMIN.equals(username) && ADMIN.equals(password)) {
            currentUser = new AdminUser(ADMIN, ADMIN);
            return LoginResult.SUCCESS.ordinal();
        }
        db = this.getReadableDatabase();
        final String query = "select * from " + TABLE_NAME
                + " where username like \'" + username + "\'";
        final Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            if (validLogin(password, cursor)) {
                currentUser = new RegUser(username, password);
                currentUser.setRealName(cursor.getString(REAL_NAME_IND));
                currentUser.setFavoriteGenre(cursor.getInt(GENRE_IND));
                currentUser.setLocation(cursor.getString(LOCATION_IND));
                currentUser.setMajor(cursor.getString(MAJOR_IND));
                currentUser.setFailedAttempts(0);
                db = this.getWritableDatabase();
                final ContentValues values = new ContentValues();
                values.put(COLUMN_ATTEMPTS, 0);
                db.update(TABLE_NAME, values, "username like \'"
                        + username + "\'", null);
                cursor.close();
                return LoginResult.SUCCESS.ordinal();
            } else {
                final String trueS = c.getString(R.string.trueS);
                db = this.getWritableDatabase();
                final ContentValues values = new ContentValues();
                values.put(COLUMN_ATTEMPTS, cursor.getInt(ATTEMPT_IND) + 1);
                if (FALSE_STRING.equals(cursor.getString(LOCKED_INDEX)) && cursor
                        .getInt(ATTEMPT_IND) >= RegUser.ATTEMPTS_ALLOWED) {
                    values.put(COLUMN_LOCKED, trueS);
                }
                db.update(TABLE_NAME, values, "username like \'"
                        + username + "\'", null);
                if (trueS.equals(cursor.getString(BAN_IND))) {
                    return LoginResult.BANNED.ordinal();
                } else if (trueS.equals(cursor.getString(LOCKED_INDEX))) {
                    return LoginResult.LOCKED.ordinal();
                }
            }
        }
        cursor.close();
        return LoginResult.NOTFOUND.ordinal();
    }

    /**
     * sees if the login is valid
     * @param password the users password
     * @param cursor the cursor for the database
     * @return true if the password is valid
     */
    private boolean validLogin(String password, Cursor cursor) {
        /*return password.equals(cursor.getString(2)) && cursor
                .getString(7).equals("false")
                && cursor.getString(8).equals("false")
                && cursor.getInt(9) < RegUser.ATTEMPTS_ALLOWED;*/
        return password.equals(cursor.getString(PASSWORD_IND)) && FALSE_STRING.equals(cursor
                .getString(BAN_IND)) && FALSE_STRING.equals(cursor.getString(LOCKED_INDEX))
                && cursor.getInt(ATTEMPT_IND) < RegUser.ATTEMPTS_ALLOWED;

    }

    /**
     * A way to add a user into the database
     * @param u the user to be added
     */
    public final void addUser(User u) {
        currentUser = u;
        db = this.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());
        values.put(COLUMN_NAME, u.getRealName());
        values.put(COLUMN_GENRE, u.getFavoriteGenreNum());
        values.put(COLUMN_LOCATION, u.getLocation());
        values.put(COLUMN_MAJOR, u.getMajor());
        values.put(COLUMN_BAN, FALSE_STRING);
        values.put(COLUMN_LOCKED, FALSE_STRING);
        values.put(COLUMN_ATTEMPTS, 0);
        values.put(KEY_ID, currentCount++);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * A method to update the information of a user
     * @param u the user to be updated
     */
    public final void updateUser(User u) {
        db = this.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, u.getRealName());
        values.put(COLUMN_GENRE, u.getFavoriteGenreNum());
        values.put(COLUMN_LOCATION, u.getLocation());
        values.put(COLUMN_MAJOR, u.getMajor());
        if (u instanceof RegUser) {
            final RegUser temp = (RegUser) u;
            values.put(COLUMN_BAN, temp.getBanStatus() ? c.getString(R.string.trueS) :
                    c.getString(R.string.falseS));
            values.put(COLUMN_LOCKED, temp.getLockStatus() ? c.getString(R.string.trueS) :
                    c.getString(R.string.falseS));
            if (!temp.getLockStatus()) {
                values.put(COLUMN_ATTEMPTS, 0);
            }
        }

        db.update(TABLE_NAME, values, "username like \'"
                + u.getUsername() + "\'", null);
        db.close();
    }

    /**
     * returns the last user who logged in
     * @return User object
     */
    public final User lastLogIn() {
        return currentUser;
    }

    /**
     * tells if a specific username is valid
     * @param u the username
     * @return true if the user is in the system
     */
    public final boolean isInSystem(String u) {
        if (ADMIN.equals(u)) {
            return true;
        }
        db = this.getReadableDatabase();
        final String query = "select username from " + TABLE_NAME
                + " where username like \'" + u + "\'";
        final Cursor cursor = db.rawQuery(query, null);
        final int temp = cursor.getCount();
        cursor.close();
        return temp > 0;
    }

    /**
     * returns a list of all users
     * @return List of users
     */
    public final List<User> getAllUsers() {
        final List<User> list = new ArrayList<>();
        db = this.getReadableDatabase();
        final String query = "select * from " + TABLE_NAME;
        final Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final RegUser tempUser = new RegUser(cursor
                    .getString(USERNAME_IND), cursor.getString(PASSWORD_IND));
            tempUser.setRealName(cursor.getString(REAL_NAME_IND));
            tempUser.setFavoriteGenre(cursor.getInt(GENRE_IND));
            tempUser.setLocation(cursor.getString(LOCATION_IND));
            tempUser.setMajor(cursor.getString(MAJOR_IND));
            if (TRUE_STRING.equals(cursor.getString(BAN_IND))) {
                tempUser.ban();
            }
            if (TRUE_STRING.equals(cursor.getString(LOCKED_INDEX))) {
                tempUser.lock();
            }
            list.add(tempUser);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * returns the context
     * @return the context
     */
    public final Context getContext() {
        return c;
    }
}
