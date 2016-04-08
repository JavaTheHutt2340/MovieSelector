package javathehutt.buzz_movieselector.model;

import java.io.Serializable;

/**
 * Created by JasonGibson on 2/2/16.
 */
public abstract class User implements Serializable {
    public static final int PRIME1 = 17;
    public static final int PRIME2 = 31;
    /**
     * the username of the user
     */
    private String username;
    /**
     * the password of the user
     */
    private String password;
    /**
     * the real name of the user
     */
    private String realName;
    /**
     * the location of the user
     */
    private String location;
    /**
     * the major of the user
     */
    private String major;
    /**
     * the favorite genre of the user
     */
    private int favoriteGenre;
    /**
     * the a list of genres of the user
     */
    private static String[] genres = {"Action & Adventure",
        "Animation", "Art House & International",
        "Classics", "Comedy", "Drama", "Horror", "Kids & Family",
        "Mystery & Suspense", "Romance", "Science Fiction & Fantasy",
        "Documentary", "Musical & Performing Arts", "Special Interest",
        "Sports & Fitness", "Television", "Western"};



    /**
    * @param name the users username
    * @param pd the users password
     */
    public User(String name, String pd) {
        this.username = name;
        this.password = pd;
        this.realName = "";
        this.location = "";
        this.favoriteGenre = 0;
        this.major = "";
    }
    /**
    * used to tell if the user is an admin user
    * @return true if the user is an admin user
     */
    public final boolean isAdmin() {
        return this instanceof AdminUser;
    }

    /**
    * returns the genres of the user
    * @return String[] genres
     */
    public static String[] getGenres() {
        return genres;
    }

    /**
     * returns the favorite genre
     * @return integer representign the genre
     */
    public final int getFavoriteGenreNum() {
        return favoriteGenre;
    }

    /**
    * sets the users logged in status to false
     */
    public abstract void logout();

    /**
    * returns true if the account is locked
    * @return boolean true if the account is locked
     */
    public abstract boolean getLockStatus();

    /**
    * returns the username
    * @return username the username the users username
     */
    public final String getUsername() {
        return username;
    }

    /**
    * returns the password
    * @return password the password the users password
     */
    protected final String getPassword() {
        return password;
    }

    /**
    * return the real name of the user
    * @return String realName the users realName
     */
    public final String getRealName() {
        return realName;
    }

    /**
    * returns the location of the user
    * @return String the location of the user
     */
    public final String getLocation() {
        return location;
    }

    /**
    * return the users favorite genre
    * @return String the users favorite genre
     */
    public final String getFavoriteGenre() {
        return genres[favoriteGenre];
    }

    /**
    * return the users major
    * @return String the users major
     */
    public final String getMajor() {
        return major;
    }

    /**
     * sets the realName of the User
     * @param s the new realName
     */
    public final void setRealName(String s) {
        realName = s;
    }

    /**
     * sets the location of the User
     * @param s the new location
     */
    public final void setLocation(String s) {
        location = s;
    }

    /**
     * sets the favoriteGenre of the User
     * @param i the new favoriteGenre
     */
    public final void setFavoriteGenre(int i) {
        favoriteGenre = i;
    }

    /**
     * sets the major of the User
     * @param s the new major
     */
    public final void setMajor(String s) {
        major = s;
    }

    /**
     * returns the number of login attempts for this user
     * @return login attempts
     */
    protected abstract int getLogAttempts();

    /**
     * set the failed attempts number
     * @param num the number to set attempts to
     */
    protected void setFailedAttempts(int num) {

    }

    /**
        * determines if the login is valid
        * @param pd the
        * @return true if logged in
         */
    public abstract boolean logIn(String pd);
    /**
    * determines if the two objects are equal
    * @param o the other object ot compare
    * @return boolean true if the objects are equal
     */
    @Override
    public final boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User other = (User) o;
        return other.getUsername().equalsIgnoreCase(this.getUsername())
                && password.equalsIgnoreCase(other.password);
    }

    /**
    * returns the hashcode of the User
    * @return int the hashCode
     */
    @Override
    public final int hashCode() {
        int result = PRIME1;
        result += PRIME2 * password.hashCode();
        result += PRIME2 * username.hashCode();
        return result;
    }

}
