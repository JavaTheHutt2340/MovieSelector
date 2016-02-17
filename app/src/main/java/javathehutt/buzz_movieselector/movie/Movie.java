package javathehutt.buzz_movieselector.movie;

/**
 * Created by Mohammed on 2/16/2016.
 */
public class Movie {
    private String name;
    private int year;
    private String mpaa_rating;
    private int runTime;
    private String criticsConsensus;
    private String criticsRating;
    private int criticsScore;
    private String audienceRating;
    private int audienceScore;

    public Movie(){}

    public Movie(String name, int year){
        this.name = name;
        this.year = year;
    }

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
}
