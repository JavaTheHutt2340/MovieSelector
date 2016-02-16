/**
 * Instantiated UserManager using maps
 * @author Mohammed Saqib
 * @version 1.0
 */
package javathehutt.buzz_movieselector.model;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
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
    @Override
    public boolean isInSystem(String u) {
        return userMap.get(u) != null;
    }
    @Override
    public boolean handleLogInRequest(String id, String password) {
        User u = userMap.get(id);
        if(u != null && u.logIn(password)) {
            currentUser = u;
            return true;
        }
        return false;
    }
    @Override
    public User lastLogIn() {
        User u = currentUser;
        return u;
    }
    @Override
    public boolean isAdmin(User u) {
        return u.isAdmin();
    }

    @Override
    public void addUser(User u) {
        String s = u.getUsername();
        userMap.put(s, u);
    }
    @Override
    public User searchUser(String s) {
        if (userMap.containsKey(s)) {
            return userMap.get(s);
        } else {
            return null;
        }
    }
    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
