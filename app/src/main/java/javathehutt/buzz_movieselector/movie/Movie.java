package javathehutt.buzz_movieselector.movie;
import java.io.Serializable;
/**
 * Created by Mohammed on 2/16/2016.
 */
public class Movie implements Serializable{
    private String name;
    private int year;
    private String mpaa_rating;
    private int runTime;
    private String criticsConsensus;
    private String criticsRating;
    private int criticsScore;
    private String audienceRating;
    private int audienceScore;

    /**
     * Standard constructor for movie
     * @param name of movie
     * @param year of movie
     */


    public Movie(String name, int year, String criticsRating, int criticsScore) {
        this.name = name;
        this.year = year;
        this.criticsRating = criticsRating;
        this.criticsScore = criticsScore;
    }

    /**
     * More indepth constructor of movie
     * @param name of movie
     * @param year of movie
     * @param mpaa_rating of movie
     * @param runtime how long movie runs
     * @param criticsConsensus short summary of critics
     * @param criticsRating Rotten or fresh or etc....
     * @param criticsScore Number score
     * @param audienceRating short summary of audience
     * @param audienceScore Number score
     */
    public Movie(String name, int year, String mpaa_rating, int runtime,
                 String criticsConsensus, String criticsRating, int criticsScore, String audienceRating,
                 int audienceScore) {
        this.name = name;
        this.year = year;
        this.mpaa_rating = mpaa_rating;
        this.runTime = runtime;
        this.criticsConsensus = criticsConsensus;
        this.criticsRating = criticsRating;
        this.criticsScore = criticsScore;
        this.audienceRating = audienceRating;
        this.audienceScore = audienceScore;
    }

    public String getName() {
        return name;
    }
    public int getYear() {
        return year;
    }
    public String getCritcsRating() {
        return criticsRating;
    }
    public int getCriticsScore() {
        return criticsScore;
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
    @Override
    public String toString() {
        return "Title: " + name + "\nYear:" + year;
    }
}
