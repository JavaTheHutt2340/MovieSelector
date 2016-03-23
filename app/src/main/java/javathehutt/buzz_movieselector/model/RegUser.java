package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class RegUser extends User{
    private boolean locked;
    private boolean banned;
    private int failedAttempts;
    public static final int ATTEMPTS_ALLOWED = 5;

    /*
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

    /*
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
    protected int getLogAttempts(){
        return failedAttempts;
    }
    /*
    * locks the user
     */
    public void lock() {
        locked = true;
    }

    /*
    * unlocks the user account
     */
    public void unlock() {
        locked = false;
    }

    /*
    * sets the user account to banned
     */
    public void ban() {
        banned = true;
    }

    /*
    * returns true if the user is banned
    * @return boolean is banned
     */
    public boolean getBanStatus() {
        return banned;
    }

    /*
    * sets the user account to be unbanned
     */
    public void unBan() {
        banned = false;
    }

    /*
    * @param password the password the person is using to login
    * @return true if the password matches the stored password
     */
    public boolean isLocked(){
        return locked;
    }

    @Override
    public boolean logIn(String password) {
        if (locked || banned) {
            return false;
        } else {
            boolean result = super.logIn(password);
            failedAttempts = result ? 0 : ++failedAttempts;
            if (failedAttempts == ATTEMPTS_ALLOWED) {
                lock();
            }
            return result;
        }
    }

    @Override
    public String toString() {
        return getUsername() + ": " + getPassword() + "\nlock status: " + locked + "\nban status: " + banned;
    }
}
