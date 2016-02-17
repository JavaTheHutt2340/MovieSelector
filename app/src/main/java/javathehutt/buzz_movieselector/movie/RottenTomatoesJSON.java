package javathehutt.buzz_movieselector.movie;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.*;

/**
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements RottenTomatoes {
    private String key = "yedukp76ffytfuy24zsqk7f5";
    @Override
    public Movie[] searchMovieByName(){
        return new Movie[0];
    };

    @Override
    public Movie[] recentDVD() {
        return new Movie[0];
    }

    @Override
    public Movie[] searchMovieByGenre() {
        return new Movie[0];
    }

    @Override
    public Movie[] recentMovies() {
        return new Movie[0];
    }

    public HttpRequest makeUrlByName(String name){
        //http://api.rottentomatoes.com/api/public/v1.0/movies.json
        //yedukp76ffytfuy24zsqk7f5
        return HttpRequest.get("http://api.rottentomatoes.com/api/public/v1.0/movies.json", true,
                "apikey", key, "q", name);
    }
    public
}
