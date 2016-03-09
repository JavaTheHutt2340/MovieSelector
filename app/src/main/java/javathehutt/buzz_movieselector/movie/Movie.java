package javathehutt.buzz_movieselector.movie;
import android.util.Log;

import java.io.Serializable;
/**
 * Created by Mohammed on 2/16/2016.
 */
public class Movie implements Serializable{
    private String name;
    private int year;
    private String criticsRating;
    private int criticsScore;
    private String synopsis;
    private String url;
    private String[] genre;
    /**
     * Standard constructor for movie
     * @param name of movie
     * @param year of movie
     * @param criticsRating representing whether movie is Rotten or otherwise
     * @param criticsScore representing numerical grade 0 - 100
     * @param synopsis String summary
     */
    public Movie(String name, int year, String criticsRating, int criticsScore, String synopsis, String url, String genre) {
        this.name = name;
        this.year = year;
        this.criticsRating = criticsRating;
        this.criticsScore = criticsScore;
        this.synopsis = synopsis;
        this.url = url;
        setGenre(genre);
    }

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

    /*
     * Accessor method to get the genre of Movie
     * @return String representing genre of Movie
     */
    public String[] getGenre() {
        return genre;
    }

    /*
     * method to handle genre input from JSON
      * @param s the list of genres
     */
    public void setGenre(String s) {
        s = s.replaceAll("\"","");
        s = s.substring(1, s.length() - 1);
        genre = s.split(",");
    }

    public boolean containsGenre(String s) {
        for (int i = 0; i < genre.length; i++) {
            Log.i("genre", name + " " + genre[i] + " bool " + s.equalsIgnoreCase(genre[i]));
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
        if(criticsRating.trim().length() == 0) {
            return "No rating yet";
        }
        return criticsRating;
    }

    /**
     * Accessor method to get critics score
     * @return String 0 - 100 or "No rating yet" string
     */
    public String getCriticsScore() {
        if (criticsScore != -1)
            return "" + criticsScore;
        return "No rating yet";
    }

    /**
     * Accessor method to get summary of movie
     * @return String of movie summary
     */
    public String getSynopsis() {
        if (synopsis != null && synopsis.length() != 0)
            return synopsis;
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
        Movie m = (Movie) o;
        return m.name.equals(name) && m.year == year;
    }

    public String getUrl() {
        return url;
    }
    @Override
    public String toString() {
        return "Title: " + getName() + "\nYear: " + getYear()  + "\nRating: " + getCriticsRating()
                + " Critics Score: " + getCriticsScore() + "\nSynopsis: " + getSynopsis() + "\n";
        //TODO overhaul this to make it limited and then click to give rest of info
    }
}
