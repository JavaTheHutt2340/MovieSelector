package javathehutt.buzz_movieselector;
        import android.app.Application;
        import android.net.nsd.NsdManager;
        import android.os.Bundle;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.runner.AndroidJUnit4;
        import android.test.ApplicationTestCase;
        import android.view.View;
        import android.widget.EditText;
        import javathehutt.buzz_movieselector.model.*;
        import static android.support.test.espresso.Espresso.onData;
        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
        import static android.support.test.espresso.action.ViewActions.typeText;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static android.support.test.espresso.matcher.ViewMatchers.withText;
        import static org.hamcrest.Matchers.instanceOf;
        import static org.hamcrest.Matchers.is;
        import static org.hamcrest.core.AllOf.allOf;


        import org.junit.BeforeClass;
        import org.junit.ClassRule;
        import org.junit.FixMethodOrder;
        import org.junit.Assert.*;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.junit.runners.MethodSorters;

        import java.util.List;

/**
 * Created by SaqibAli on 4/4/16.
 */
@RunWith(AndroidJUnit4.class)
public class SaqibTest extends ApplicationTestCase<Application> {
    private static DatabaseHelper db;
    private static RegUser u = new RegUser("bob", "bob");
    public SaqibTest() {
        super(Application.class);
    }
    @ClassRule
    public static ActivityTestRule<MainActivity> activity = new ActivityTestRule(MainActivity.class);

    @BeforeClass
    public static void setup() {
        db = new DatabaseHelper(activity.getActivity().getApplicationContext());
        db.addUser(u);
    }
    @Test
    public void testUpdateUser() {
        u.setFavoriteGenre(2);
        u.setLocation("Georgia");
        u.setMajor("Computer Science");
        u.setRealName("Saqib");
        db.updateUser(u);
        RegUser user1 = new RegUser("Carl", "Carl");
        for (User user : db.getAllUsers()) {
            if (user.equals(u)) {
                user1 = (RegUser) user;
            }
        }
        assertTrue(user1.getLocation().equals("Georgia"));
    }
}
