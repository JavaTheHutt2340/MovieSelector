package javathehutt.buzz_movieselector.model;
import javathehutt.buzz_movieselector.model.User;
/**
 * Created by Mohammed on 2/3/2016.
 * will merge with other UserManager if necessary
 */
public interface UserManager {
    boolean isUser(User u);
    boolean isAdmin(User u);
    void addUser(User u);
    User search(String id);
}
