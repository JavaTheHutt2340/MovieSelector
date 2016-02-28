package javathehutt.buzz_movieselector.model;

import com.facebook.Profile;

/**
 * Created by Mohammed on 2/28/2016.
 */
public class FacebookUser extends RegUser{
    private Profile facebookProfile;
    public FacebookUser(Profile p) {
        super(p.getId(),p.getId());
        setRealName(facebookProfile.getFirstName() + " " + facebookProfile.getLastName());
        facebookProfile = p;
    }

    public boolean logIn(String password) {
        if(Profile.getCurrentProfile().equals(facebookProfile) && super.logIn(password)) {
            return true;
        } else {
            Profile.setCurrentProfile(null);
            return false;
        }
    }
    @Override
    public void logout() {
        Profile.setCurrentProfile(null);
        super.logout();
    }
}
