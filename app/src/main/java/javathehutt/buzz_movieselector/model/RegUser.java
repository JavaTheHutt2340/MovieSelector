package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class RegUser extends User {
    /**
     * if the user is locked
     */
    private boolean locked;
    /**
     * if the user is banned
     */
    private boolean banned;
    /**
     * number of failed attempts
     */
    private int failedAttempts;
    /**
     * the number of failed attempts allowed
     */
    public static final int ATTEMPTS_ALLOWED = 5;

    /**
    * constructor for the User class
    * @param username the users username
    * @param password the users password
     */
    public RegUser(String username, String password) {
        super(username, password);
        failedAttempts = 0;
        locked = false;
        banned = false;
    }

    /**
    * used to determined if the account is locked
    * @return locked true if the user is locked false otherwise
     */
    @Override
    public boolean getLockStatus() {
        return locked;
    }

    @Override
    protected void setFailedAttempts(int num) {
        failedAttempts = num;
    }

    @Override
    protected int getLogAttempts() {
        return failedAttempts;
    }
    /**
    * locks the user
     */
    public void lock() {
        locked = true;
    }

    /**
    * unlocks the user account
     */
    public void unlock() {
        locked = false;
    }

    /**
    * sets the user account to banned
     */
    public void ban() {
        banned = true;
    }

    /**
    * returns true if the user is banned
    * @return boolean is banned
     */
    public boolean getBanStatus() {
        return banned;
    }

    /**
    * sets the user account to be unbanned
     */
    public void unBan() {
        banned = false;
    }

    /**
    * @return true if the user is locked
     */
    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean logIn(String password) {
        if (locked || banned) {
            return false;
        } else {
            final boolean result = super.logIn(password);
            failedAttempts = result ? 0 : ++failedAttempts;
            if (failedAttempts == ATTEMPTS_ALLOWED) {
                lock();
            }
            return result;
        }
    }

    @Override
    public String toString() {
        return getUsername() + ": " + getPassword() + "\nlock status: "
                + locked + "\nban status: " + banned;
    }
}
