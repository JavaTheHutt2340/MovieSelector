package javathehutt.buzz_movieselector.model;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Mohammed on 2/3/2016.
 */
public class UserMapManager implements UserManager {
    private Map<String, User> userMap;
    public UserMapManager() {
        userMap = new HashMap<>();
    }
    public boolean isUser(User u) {
        return true;
    }
    public boolean isAdmin(User u) {
        return true;
    }
    public void addUser(User u) {

    }
    public User search(String s) {
        return new RegUser(null, null);
    }
}
