package javathehutt.buzz_movieselector.model;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Mohammed on 2/3/2016.
 */
public class UserMapManager implements UserManager {

    private static Map<String, User> userMap;
    private static User currentUser;
    /**
     * Constructor for UserMapManager
     */
    public UserMapManager() {
        if (userMap == null) {
            userMap = new HashMap<>();
        }
    }

    /**
     * Method for checking if a username corresponds
     *  with existing user
     * @param u String representing username
     * @return true if is in map
     */
    public boolean isInSystem(String u) {
        return userMap.get(u) != null;
    }

    /**
     * Method for safely handling Log In based on username and password
     * @param id String representing username
     * @param password String representing password
     * @return true if login was successful
     */
    public boolean handleLogInRequest(String id, String password) {
        User u = userMap.get(id);
        if(u != null && u.logIn(password)) {
            currentUser = u;
            return true;
        }
        return false;
    }

    /**
     * A method for returning the last User who was logged in
     * @return User object
     */
    public User lastLogIn() {
        User u = currentUser;
        return u;
    }

    /**
     * A method for checking if User is an Admin user
     * @param u User object to check
     * @return true if it is
     */
    public boolean isAdmin(User u) {
        return u.isAdmin();
    }

    /**
     * A method to add a user to the database
     * @param u User to add
     */
    public void addUser(User u) {
        String s = u.getUsername();
        userMap.put(s, u);
    }

    /**
     * A method to search a user based on username
     * @param s String representing username
     * @return User object corresponding to username
     */
    public User searchUser(String s) {
        if (userMap.containsKey(s)) {
            return userMap.get(s);
        } else {
            return null;
        }
    }

    /**
     * A method to return current user in system
     * @return User object
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
