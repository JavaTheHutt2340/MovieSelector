package javathehutt.buzz_movieselector.movie;

import java.io.Serializable;
/**
 * Created by Mohammed on 2/16/2016.
 */
public class Movie implements Serializable {
    private String name;
    private int year;
    private String criticsRating;
    private int criticsScore;
    private String synopsis;
    private String apiUrl;
    private String[] genre;
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
    public String getName() {
        return name;
    }

    /**
     * gets the name of the movie
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor method to get year of Movie
     * @return int representing year of Movie
     */
    public int getYear() {
        return year;
    }

    /**
     * Accessor method to get the genre of Movie
     * @return String representing genre of Movie
     */
    public String[] getGenre() {
        return genre;
    }

    /**
     * method to handle genre input from JSON
      * @param s the list of genres
     */
    public void setGenre(String s) {
        s = s.replaceAll("\"", "");
        s = s.substring(1, s.length() - 1);
        genre = s.split(",");
    }

    /**
     * Checks Movie to see if it is a Genre
     * @param s String representing Genre
     * @return true if it is genre
     */
    public boolean containsGenre(String s) {
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
    public String getCriticsRating() {
        if (criticsRating.trim().length() == 0) {
            return "No rating yet";
        }
        return criticsRating;
    }

    /**
     * Accessor method
     * @return criticsScore
     */
    public int getCriticsScoreInt() {
        return criticsScore;
    }

    /**
     * Accessor method to get critics score
     * @return String 0 - 100 or "No rating yet" string
     */
    public String getCriticsScore() {
        if (criticsScore != -1) {
            return Integer.toString(criticsScore);
        }
        return "No rating yet";
    }

    /**
     * Accessor method to get summary of movie
     * @return String of movie summary
     */
    public String getSynopsis() {
        if (synopsis != null && synopsis.length() != 0) {
            return synopsis;
        }
        return "No synopsis";
    }

    @Override
    public int hashCode() {
        int h = 17;
        h += 31 * name.hashCode();
        h += 31 * year;
        return h;
    }
    @Override
    public boolean equals(Object o) {
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
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Getter Method for alternate URL
     *  URL that goes to actual rotten tomatoes website
     * @return String
     */
    public String getAltUrl() {
        return altUrl;
    }

    /**
     * Setter method for alternate URL
     * @param altUrl the alt url for the movie
     */
    public void setAltUrl(String altUrl) {
        this.altUrl = altUrl;
    }

    @Override
    public String toString() {
        return "Title: " + getName() + "\nYear: " + getYear()
                + "\nRating: " + getCriticsRating()
                + " Critics Score: " + getCriticsScore();
    }
}
