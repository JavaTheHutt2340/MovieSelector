package javathehutt.buzz_movieselector;

import android.app.Application;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Log;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.RegUser;
import javathehutt.buzz_movieselector.model.User;

import static org.junit.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    static DatabaseHelper db;

    @ClassRule
    public static ActivityTestRule<MainActivity> activity = new ActivityTestRule(MainActivity.class);

    @BeforeClass
    public static void setup() {
        db = new DatabaseHelper(activity.getActivity().getApplicationContext());
        db.addUser(new RegUser("bob", "bob"));
        db.updateUser(new RegUser("bob", "bob"));

        //if (!db.isInSystem("banned")) {
            RegUser u1 = new RegUser("banned", "banned");
            u1.ban();
            db.updateUser(u1);
            db.addUser(u1);
        //}

        //if (!db.isInSystem("locked")) {
            RegUser u2 = new RegUser("locked", "locked");
            u2.lock();
            db.updateUser(u2);
            db.addUser(u2);
        //}


    }

    @Test
    public void a01validLogin() {
        assertEquals(db.handleLogInRequest("bob", "bob"), 0);
    }

    @Test
    public void a02adminUser() {
        assertEquals(db.handleLogInRequest("admin", "admin"), 0);
    }

    @Test
    public void a03invalidPassword() {
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
    }

    @Test
    public void a04locked() {
        assertEquals(db.handleLogInRequest("locked", "locked"), 3);
    }

    @Test
     public void a05banned() {
        assertEquals(db.handleLogInRequest("banned", "banned"), 2);
    }

    @Test
    public void a06lockAccount() {
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
        assertEquals(db.handleLogInRequest("bob", "not bob"), 3);
    }


}