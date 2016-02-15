package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public abstract class User {
    private String username;
    private String password;
    private boolean loggedIn;


    /*
    * @param username the users username
    * @param password the users password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        loggedIn = false;
    }
    /*
    * used to tell if the user is an admin user
    * @return true if the user is an admin user
     */
    public boolean isAdmin() {
        return this instanceof AdminUser;
    }

    /*
    * sets the users logged in status to false
     */
    public void logout() {
        loggedIn = false;
    }

    /*
    *
     */
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
        return other.getUsername().equals(this.getUsername())
                && password.equals(other.password);
    }

    public int hashCode() {
        int result = 0;
        result *= 17 * password.hashCode();
        result *= 21 * username.hashCode();
        return result;
    }

}
