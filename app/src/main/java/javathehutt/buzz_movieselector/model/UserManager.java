package javathehutt.buzz_movieselector.model;
import javathehutt.buzz_movieselector.model.User;
/**
 * Created by Mohammed on 2/3/2016.
 *
 */
public interface UserManager {
    /**
     * Method for checking if a username corresponds
     *  with existing user
     * @param u String representing username
     * @return true if is in map
     */
    boolean isInSystem(String u);

    /**
     * A method for checking if User is an Admin user
     * @param u User object to check
     * @return true if it is
     */
    boolean isAdmin(User u);

    /**
     * Method for safely handling Log In based on username and password
     * @param id String representing username
     * @param password String representing password
     * @return true if login was successful
     */
    boolean handleLogInRequest(String id, String password);

    /**
     * A method to add a user to the database
     * @param u User to add
     */
    void addUser(User u);

    /**
     * A method for returning the last User who was logged in
     * @return User object
     */
    User lastLogIn();

    /**
     * A method to search a user based on username
     * @param id String representing username
     * @return User object corresponding to username
     */
    User searchUser(String id);

    /**
     * A method to return current user in system
     * @return User object
     */
    User getCurrentUser();
}
