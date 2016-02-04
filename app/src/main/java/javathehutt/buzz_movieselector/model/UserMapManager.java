package javathehutt.buzz_movieselector.model;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Mohammed on 2/3/2016.
 */
public class UserMapManager implements UserManager, AuthenticationManager {
    private Map<String, User> userMap;
    public UserMapManager() {
        userMap = new HashMap<>();
    }
    public boolean isInSystem(String u) {
        return userMap.get(u) != null;
    }
    public boolean handleLogInRequest(String id, String password) {
        User u = userMap.get(id);
        return u.logIn(password);
    }
    public boolean isAdmin(User u) {
        return u.isAdmin();
    }
    public void addUser(User u) {
        String s = u.getUsername();
        userMap.put(s, u);
    }
    public User searchUser(String s) {
        return new RegUser(null, null);
    }
}
