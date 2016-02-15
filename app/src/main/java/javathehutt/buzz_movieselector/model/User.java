package javathehutt.buzz_movieselector.model;

/**
 * Created by JasonGibson on 2/2/16.
 */
public abstract class User {
    private String username;
    private String password;
    private String realName;
    private String location;
    private String favoriteGenre;
    private boolean loggedIn;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.realName = "";
        this.location = "";
        this.favoriteGenre = "";
        loggedIn = false;
    }
    public boolean isAdmin() {
        return this instanceof AdminUser;
    }
    public void logout() {
        loggedIn = false;
    }
    public abstract boolean getLockStatus();
    public String getUsername() { return username; }
    public String getRealName() { return realName; }
    public String getLocation() { return location; }
    public String getFavoriteGenre() { return favoriteGenre; }

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
