package javathehutt.buzz_movieselector;

import android.content.Context;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.RegUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.SharedPreferences;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel on 4/4/2016.
 */
public class DanTest {
    DatabaseHelper dp;
    @ClassRule
    public static ActivityTestRule<LoginActivity> activity = new ActivityTestRule(LoginActivity.class);

    @Before
    public void setup(){
        dp = new DatabaseHelper(activity.getActivity());
        dp.addUser(new RegUser("user","pass"));
    }

    @Test
    public void testIsInSystem() throws Exception {
        assertEquals(dp.isInSystem("user"), false);
        //RegUser u = new RegUser("user", "pass");
        //dp.addUser(u);
        //assertEquals(dp.isInSystem(u.getUsername()), true);
    }
}