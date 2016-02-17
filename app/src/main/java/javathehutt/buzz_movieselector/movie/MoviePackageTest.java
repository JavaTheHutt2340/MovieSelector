package javathehutt.buzz_movieselector.movie;

import com.github.kevinsawicki.http.HttpRequest;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;

/**
 * Created by Mohammed on 2/16/2016.
 */
public class MoviePackageTest {
    private final int TIMEOUT = 200;
    private HttpRequest h;
    private RottenTomatoesJSON control;
    @Before
    public void prep(){
        control = new RottenTomatoesJSON();
        h = control.makeRequestByName("Toy Story 3");
    }
    @Test(timeout = TIMEOUT)
    public void testMakeMovie(){
        HttpRequest r = control.makeRequestByName("Toy Story 3");
        BufferedReader b = control.extractReader(r);
        Movie m = control.makeMovie(b);
    }
}
