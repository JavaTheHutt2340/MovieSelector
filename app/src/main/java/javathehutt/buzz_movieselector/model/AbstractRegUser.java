package javathehutt.buzz_movieselector.model;

/**
 * Created by Mohammed on 4/7/2016.
 */
public abstract class AbstractRegUser extends User {
    /**
     * if the user is banned
     */
    private boolean banned;
    public AbstractRegUser(String user, String pd) {
        super(user, pd);
        banned = false;
    }
    /**
     * sets the user account to banned
     */
    public final void ban() {
        banned = true;
    }

    /**
     * returns true if the user is banned
     * @return boolean is banned
     */
    public final boolean getBanStatus() {
        return banned;
    }

    /**
     * sets the user account to be unbanned
     */
    public final void unBan() {
        banned = false;
    }


}
