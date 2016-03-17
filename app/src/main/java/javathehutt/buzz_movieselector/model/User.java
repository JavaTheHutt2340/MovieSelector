package javathehutt.buzz_movieselector.model;

import java.io.Serializable;

/**
 * Created by JasonGibson on 2/2/16.
 */
public abstract class User implements Serializable {
    private String username;
    private String password;
    private String realName;
    private String location;
    private String major;
    private boolean loggedIn;
    private int favoriteGenre;
    private static String[] genres = {"Action & Adventure", "Animation", "Art House & International",
            "Classics", "Comedy", "Drama", "Horror", "Kids & Family", "Mystery & Suspense", "Romance", "Science Fiction & Fantasy",
            "Documentary", "Musical & Performing Arts", "Special Interest", "Sports & Fitness", "Television", "Western"};



    /*
    * @param username the users username
    * @param password the users password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.realName = "";
        this.location = "";
        this.favoriteGenre = 0;
        this.major = "";
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
    * returns the genres of the user
    * @return String[] genres
     */
    public static String[] getGenres() {
        return genres;
    }

    public int getFavoriteGenreNum() {
        return favoriteGenre;
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
    * returns the password
    * @param String the password the users password
     */
    public String getPassword() { return password; }
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
    public String getFavoriteGenre() { return genres[favoriteGenre]; }

    /*
    * return the users major
    * @return String the users major
     */
    public String getMajor() { return major; }

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
    public void setFavoriteGenre(int i) {
        favoriteGenre = i;
    }

    /*
     * sets the major of the User
     * @param s the new major
     */
    public void setMajor(String s) {
        major = s;
    }

    /*
    * returns the number of login attempts for this user
     */
    protected int getLogAttempts() {
        return 0;
    }

    protected void setFailedAttempts(int num) {};

    /*
        * determines if the login is valid
        * @param password the
         */
    public boolean logIn(String password) {
        boolean result = this.password.equals(password);
        loggedIn = result;
        return result;
    }

    /**
     * standard getter method for loggedIn
     * @return true if loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
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
    @Override
    public int hashCode() {
        int result = 0;
        result += 17 * password.hashCode();
        result += 21 * username.hashCode();
        return result;
    }
    @Override
    public String toString() {
        return username + ": " + password;
    }

}
