package javathehutt.buzz_movieselector;
import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javathehutt.buzz_movieselector.movie.MovieSource;
import javathehutt.buzz_movieselector.movie.RottenTomatoesJSON;

/**
 * Created by Mohammed on 4/3/2016.
 */
public class MohammedTest {
    private static MovieSource ms;
    @ClassRule
    public static ActivityTestRule<MovieSearchActivity> activity = new ActivityTestRule(MovieSearchActivity.class);

    @BeforeClass
    public static void setUp() {
        ms = new RottenTomatoesJSON(activity.getActivity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        ms.searchMovieByName(null, 10, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoName() {
        ms.searchMovieByName("", 10, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroLimit() {
        ms.searchMovieByName("Toy Story", 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeLimit() {
        ms.searchMovieByName("Toy Story", -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativePageNo() {
        ms.searchMovieByName("Toy Story", 1, -1);
    }

    @Test
    public void testValidSearch() {
        ms.searchMovieByName("Toy Story", 1, 1);
    }

}
