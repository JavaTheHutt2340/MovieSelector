package javathehutt.buzz_movieselector.model;

/**
 * Created by Mohammed on 2/4/2016.
 */
public interface AuthenticationManager {
    boolean handleLogInRequest(String id, String password);
}
