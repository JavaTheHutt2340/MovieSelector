package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public abstract class User {
    private String username;
    private String password;
    private boolean loggedIn;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
        loggedIn = false;
    }
    public boolean isAdmin() {
        return this instanceof AdminUser;
    }
    public void logout() {
        loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public boolean logIn(String password) {
        boolean result = this.password.equals(password);
        loggedIn = result;
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return other.getUsername().equals(this.getUsername());
    }
}
