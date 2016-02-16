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


    /*
    * @param username the users username
    * @param password the users password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.realName = "";
        this.location = "";
        this.favoriteGenre = "";
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
    * returns true if the account is locked
    * @return boolean true if the account is locked
     */

    public abstract boolean getLockStatus();

    /*
    * returns the username
    * @param String the username the users username
     */
    public String getUsername() { return username; }
    /*
    * return the real name of the user
    * @return String realName the users realName
     */
    public String getRealName() { return realName; }

    /*
    * returns the location of the user
    * @return String the location of the user
     */
    public String getLocation() { return location; }

    /*
    * return the users favorite genre
    * @return String the users favorite genre
     */
    public String getFavoriteGenre() { return favoriteGenre; }

    /*
     * sets the realName of the User
     * @param s the new realName
     */
    public void setRealName(String s) {
        realName = s;
    }

    /*
     * sets the location of the User
     * @param s the new location
     */
    public void setLocation(String s) {
        location = s;
    }

    /*
     * sets the favoriteGenre of the User
     * @param s the new favoriteGenre
     */
    public void setFavoriteGenre(String s) {
        favoriteGenre = s;
    }

    /*
    * determines if the login is valid
    * @param password the
     */
    public boolean logIn(String password) {
        boolean result = this.password.equals(password);
        loggedIn = result;
        return result;
    }
    /*
    * determines if the two objects are equal
    * @param o the other object ot compare
    * @return boolean true if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return other.getUsername().equalsIgnoreCase(this.getUsername())
                && password.equalsIgnoreCase(other.password);
    }

    /*
    * returns the hashcode of the User
    * @return int the hashCode
     */
    public int hashCode() {
        int result = 0;
        result += 17 * password.hashCode();
        result += 21 * username.hashCode();
        return result;
    }

}
