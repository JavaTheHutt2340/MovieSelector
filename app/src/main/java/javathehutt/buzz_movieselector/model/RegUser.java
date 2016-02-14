package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class RegUser extends User{
    private boolean locked;
    private int failedAttempts;
    private final int attemptsAllowed = 5;

    public RegUser(String username, String password) {
        super(username, password);
        locked = false;
    }

    public boolean getLockStatus() {
        return locked;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

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
