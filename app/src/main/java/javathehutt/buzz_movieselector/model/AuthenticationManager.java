package javathehutt.buzz_movieselector.model;

/**
 * Created by Mohammed on 2/6/2016.
 */
public interface AuthenticationManager {
    boolean handleLogInRequest(String id, String password);
    User lastLogIn();
}
