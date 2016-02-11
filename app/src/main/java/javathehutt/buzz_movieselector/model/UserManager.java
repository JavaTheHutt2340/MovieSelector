package javathehutt.buzz_movieselector.model;
import javathehutt.buzz_movieselector.model.User;
/**
 * Created by Mohammed on 2/3/2016.
 * will merge with other UserManager if necessary
 */
public interface UserManager {
    boolean isInSystem(String u);
    boolean isAdmin(User u);
    boolean handleLogInRequest(String id, String password);
    void addUser(User u);
    User searchUser(String id);
    User getCurrentUser();
    User lastLogIn();
}
