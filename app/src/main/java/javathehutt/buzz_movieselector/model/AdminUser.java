package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public class AdminUser extends User {
    public AdminUser(String username, String password) {
        super(username, password);
    }

    public boolean getLockStatus() {
        return false;
    }

    public void unlockAccount(RegUser user) {
        if (user.getLockStatus()) {
            user.unlock();
        } else {
            //throw exception
            throw new IllegalStateException("Account already unlocked");
        }
    }

    public void lockAccount(RegUser user) {
        if (!user.getLockStatus()) {
            user.lock();
        } else {
            //throw exception
            throw new IllegalStateException("Account already locked");
        }
    }
}
