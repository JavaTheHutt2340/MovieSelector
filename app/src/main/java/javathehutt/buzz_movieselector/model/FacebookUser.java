package javathehutt.buzz_movieselector.model;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

/**
 * Created by Mohammed on 2/28/2016.
 */
public class FacebookUser extends RegUser{
    private LoginManager loginManager;
    private Profile facebookProfile;
    public FacebookUser(Profile p) {
        super(p.getId(),p.getId());
        loginManager = LoginManager.getInstance();
        setRealName(facebookProfile.getFirstName() + " " + facebookProfile.getLastName());
        facebookProfile = p;
    }

    public boolean logIn(String password) {
        if(Profile.getCurrentProfile().equals(facebookProfile) && super.logIn(password)) {
            return true;
        } else {
            loginManager.logOut();
            return false;
        }
    }
    @Override
    public void logout() {
        loginManager.logOut();
        super.logout();
    }
}
