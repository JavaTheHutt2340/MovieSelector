package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class AdminUser extends User {
    public AdminUser(String username, String password) {
        super(username, password);
    }

    /*
    * returns if the account is locked
    * @return booolean true if the account is locked
     */
    public boolean isLocked() {
        return false;
    }

    /*
    * unlocks a RegUser
    * @param the user to be unlocked
     */
    public void unlockAccount(RegUser user) {
        if (user.getLockStatus()) {
            user.unlock();
        } else {
            throw new IllegalStateException("Account already unlocked");
        }
    }
}
