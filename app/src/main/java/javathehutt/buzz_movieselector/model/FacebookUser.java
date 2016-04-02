package javathehutt.buzz_movieselector.model;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

/**
 * Created by Mohammed on 2/28/2016.
 */
public class FacebookUser extends RegUser {
    private AccessToken at;
    private String id;

    /**
     * creates a facebook user
     * @param name users name
     * @param id users id
     * @param at users access token
     */
    public FacebookUser(String name, String id, AccessToken at) {
        super(name, id);
        this.id = id;
        this.at = at;
    }


    @Override
    public boolean logIn(String password) {
        return at.getUserId() == id;
    }
    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
        at = null;
        super.logout();
    }
}
