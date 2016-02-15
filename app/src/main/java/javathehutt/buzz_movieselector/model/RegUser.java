package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class RegUser extends User{
    private boolean locked;
    private int failedAttempts;
    private final int attemptsAllowed = 5;

    /*
    * constructor for the User class
    * @param username the users username
    * @param password the users password
     */
    public RegUser(String username, String password) {
        super(username, password);
        locked = false;
    }

    /*
    * used to determined if the account is locked
    * @return locked ture if the user is locked false otherwise
     */
    public boolean getLockStatus() {
        return locked;
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
    * @param password the password the person is using to login
    * @return true if the password matches the stored password
     */
    public boolean isLocked(){
        return locked;
    }

    @Override
    public boolean logIn(String password) {
        if (locked) {
            return false;
        } else {
            boolean result = super.logIn(password);
            failedAttempts = result ? 0 : ++failedAttempts;
            if (failedAttempts == attemptsAllowed) {
                lock();
            }
            return result;
        }
    }
}
