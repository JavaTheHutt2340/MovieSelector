package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class AdminUser extends User {
    /**
     * admin user constructor
     * @param username username
     * @param password password
     */
    public AdminUser(String username, String password) {
        super(username, password);
    }

    @Override
    public final boolean logIn(String pd) {
        return pd.equals(getPassword());
    }

    @Override
    public void logout(){}

    /**
    * returns if the account is locked
    * @return boolean true if the account is locked
     */
    @Override
    public final boolean getLockStatus() {
        return false;
    }

    /**
    * unlocks a RegUser
    * @param user to be unlocked
     */
    public final void unlockAccount(RegUser user) {
        if (user.getLockStatus()) {
            user.unlock();
        } else {
            throw new IllegalStateException("Account already unlocked");
        }
    }

    /**
    * locks the given account
    * @param user to be banned
     */
    public final void lockAccount(RegUser user) {
        if (!user.getLockStatus()) {
            user.lock();
        } else {
            throw new IllegalStateException("Account already locked");
        }
    }

    @Override
    public final int getLogAttempts() {
        return 0;
    }
}
