package javathehutt.buzz_movieselector.movie;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements RottenTomatoes {
    private final String KEY = "yedukp76ffytfuy24zsqk7f5";
    private final int TIMEOUT = 200;
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

    public HttpRequest makeRequestByName(String name){
        //http://api.rottentomatoes.com/api/public/v1.0/movies.json
        //yedukp76ffytfuy24zsqk7f5
        try {
            return HttpRequest.get("http://api.rottentomatoes.com/api/public/v1.0/movies.json", true,
                    "apikey", KEY, "q", name);
        } catch (HttpRequest.HttpRequestException e) {
            return null;
        }
    }
    public BufferedReader extractReader(HttpRequest h) {
        try {
            if (h.ok()) {
                return h.bufferedReader("\",:{}[]");
            }
            return null;
        } catch (HttpRequest.HttpRequestException e) {
            return null;
        }
    }

    public Movie makeMovie(BufferedReader b) {
        Scanner scan = new Scanner(b);
        String title = "";
        int year = 0;
        while(scan.hasNext()) {
            String toProcess = scan.next();
            if(toProcess.equals("title")) {
                title = scan.next();
            }
            if(toProcess.equals("year")) {
                year = new Integer(scan.next());
            }
        }
        return new Movie(title, year);
    }


}
