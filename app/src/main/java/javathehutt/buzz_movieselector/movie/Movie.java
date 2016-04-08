package javathehutt.buzz_movieselector.movie;

import java.io.Serializable;
/**
 * Created by Mohammed on 2/16/2016.
 */
public class Movie implements Serializable {

    public static final int PRIME1 = 17;
    public static final int PRIME2 = 31;

    /**
     * name of the movie
     */
    private String name;
    /**
     * year of the movie
     */
    private int year;
    /**
     * critics rating of the movie
     */
    private String criticsRating;
    /**
     * critics score of the movie
     */
    private int criticsScore;
    /**
     * synopsis of the movie
     */
    private String synopsis;
    /**
     * api url of the movie
     */
    private String apiUrl;
    /**
     * genrel of the movie
     */
    private String[] genre;
    /**
     * the alternate url of the movie
     */
    private String altUrl;
    /**
     * Standard constructor for movie
     * @param mname of movie
     * @param myear of movie
     * @param mcriticsRating representing whether movie is Rotten or otherwise
     * @param mcriticsScore representing numerical grade 0 - 100
     * @param msynopsis String summary
     * @param mgenre the genre of the movie
     * @param murl the url for the movie
     */
    public Movie(String mname, int myear, String mcriticsRating,
                 int mcriticsScore, String msynopsis, String murl, String mgenre) {
        this.name = mname;
        this.year = myear;
        this.criticsRating = mcriticsRating;
        this.criticsScore = mcriticsScore;
        this.synopsis = msynopsis;
        this.apiUrl = murl;
        setGenre(mgenre);
    }

    /**
     * movie constructor
     */
    public Movie() {
        this(null, 0, null, 0, null, null, null);
    }

    /**
     * Accessor method to get name of Movie
     * @return String representing name of Movie
     */
    public final String getName() {
        return name;
    }

    /**
     * gets the name of the movie
     * @param nname the name
     */
    public final void setName(String nname) {
        this.name = nname;
    }

    /**
     * Accessor method to get year of Movie
     * @return int representing year of Movie
     */
    public final int getYear() {
        return year;
    }

    /**
     * Accessor method to get the genre of Movie
     * @return String representing genre of Movie
     */
    public final String[] getGenre() {
        return genre;
    }

    /**
     * method to handle genre input from JSON
      * @param s the list of genres
     */
    public final void setGenre(String s) {
        String result;
        result = s.replaceAll("\"", "");
        result = result.substring(1, result.length() - 1);
        genre = result.split(",");
    }

    /**
     * Checks Movie to see if it is a Genre
     * @param s String representing Genre
     * @return true if it is genre
     */
    public final boolean containsGenre(String s) {
        for (int i = 0; i < genre.length; i++) {
            if (s.equalsIgnoreCase(genre[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Accessor method to get critics rating
     * @return String representing fresh, rotten, or in between movie
     */
    public final String getCriticsRating() {
        if (criticsRating.trim().length() == 0) {
            return "No rating yet";
        }
        return criticsRating;
    }

    /**
     * Accessor method
     * @return criticsScore
     */
    public final int getCriticsScoreInt() {
        return criticsScore;
    }

    /**
     * Accessor method to get critics score
     * @return String 0 - 100 or "No rating yet" string
     */
    public final String getCriticsScore() {
        if (criticsScore != -1) {
            return Integer.toString(criticsScore);
        }
        return "No rating yet";
    }

    /**
     * Accessor method to get summary of movie
     * @return String of movie summary
     */
    public final String getSynopsis() {
        if (synopsis != null && synopsis.length() != 0) {
            return synopsis;
        }
        return "No synopsis";
    }

    @Override
    public final int hashCode() {
        int h = PRIME1;
        h += PRIME2 * name.hashCode();
        h += PRIME2 * year;
        return h;
    }
    @Override
    public final boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        final Movie m = (Movie) o;
        return m.name.equals(name) && m.year == year;
    }

    /**
     * Accessor method to API url for REST calls
     * @return URL string
     */
    public final String getApiUrl() {
        return apiUrl;
    }

    /**
     * Getter Method for alternate URL
     *  URL that goes to actual rotten tomatoes website
     * @return String
     */
    public final String getAltUrl() {
        return altUrl;
    }

    /**
     * Setter method for alternate URL
     * @param maltUrl the alt url for the movie
     */
    public final void setAltUrl(String maltUrl) {
        this.altUrl = maltUrl;
    }

    @Override
    public final String toString() {
        return "Title: " + getName() + "\nYear: " + getYear()
                + "\nRating: " + getCriticsRating()
                + " Critics Score: " + getCriticsScore();
    }
}
