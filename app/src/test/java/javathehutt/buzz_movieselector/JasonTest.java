package javathehutt.buzz_movieselector;


import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.RegUser;

import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by JasonGibson on 4/2/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class JasonTest {
    DatabaseHelper db;

    @Mock
    Context mMockContext;

    @Before
    public void setup() {
        db = new DatabaseHelper(mMockContext);
        db.addUser(new RegUser("bob", "bob"));
    }

    @Test
    public void invalidUsername() {
        assertEquals(db.handleLogInRequest("bob", "not bob"), 1);
    }
}
