package javathehutt.buzz_movieselector;
        import android.app.Application;
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
        import org.junit.Assert;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.junit.runners.MethodSorters;

        import java.util.List;

/**
 * Created by SaqibAli on 4/4/16.
 */
@RunWith(AndroidJUnit4.class)
public class SaqibTest extends ApplicationTestCase<Application> {
    private static DatabaseHelper DBH;
    public SaqibTest() {
        super(Application.class);
    }
    @ClassRule
    public static ActivityTestRule<DetailedUserView> activity = new ActivityTestRule(DetailedUserView.class);

    @BeforeClass
    public static void setup() {
        Bundle b = new Bundle();
        RegUser regUser = new RegUser("hello", "buy");
        b.putSerializable("user", regUser);
        activity.getActivity().onCreate(b);
        DependencyInjectionContainer DIC = new DependencyInjectionContainer(activity.getActivity());
        DBH = DIC.getDatabaseDep();
        DBH.addUser(regUser);
    }
    @Test
    public void testIfLocked() {
        //onView(withId(R.id.lockButton)).perform(click());
        //onView(withId(R.id.lockButton)).check(matches(withText("unlock account")));
        List<User> users = DBH.getAllUsers();
        RegUser user = new RegUser("goodbye", "buenos diaz");
        for (User u : users) {
            if (u.getUsername().equals("hello")) {
                user = (RegUser) u;
            }
        }
        Assert.assertTrue(user.getLockStatus());
    }

    @Test
    public void testIfUnlocked() {
        //onView(withId(R.id.lockButton)).perform(click());
        //onView(withId(R.id.lockButton)).check(matches(withText("unlock account")));
        List<User> users = DBH.getAllUsers();
        RegUser user = new RegUser("goodbye", "buenos diaz");
        for (User u : users) {
            if (u.getUsername().equals("hello")) {
                user = (RegUser) u;
            }
        }
        Assert.assertTrue(!user.getLockStatus());
    }


}
