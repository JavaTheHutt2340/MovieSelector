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
     * @param userid users id
     * @param atoken users access token
     */
    public FacebookUser(String name, String userid, AccessToken atoken) {
        super(name, userid);
        this.id = userid;
        this.at = atoken;
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
