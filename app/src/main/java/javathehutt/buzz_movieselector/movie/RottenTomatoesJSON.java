package javathehutt.buzz_movieselector.movie;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;

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
    public InputStreamReader extractReader(HttpRequest h) {
        try {
            if (h.ok()) {
                return h.reader();
            }
            return null;
        } catch (HttpRequest.HttpRequestException e) {
            return null;
        }
    }

    public Movie[] makeMovie(InputStreamReader b) {
        ArrayList<Movie> movies = new ArrayList<>();
        Scanner scan = new Scanner(b).useDelimiter("\"");
        String total = "";
        total = scan.nextLine();
        /*while(scan.hasNext()) {

            String toProcess = scan.next();
            if(toProcess.equals("title")) {
                title = scan.next();
            }
            if(toProcess.equals("year")) {
                year = new Integer(scan.next());
            }
        }*/
        while(total.length() != 0) {
            total = total.substring(total.indexOf("title\":\"") + 8);
            int nextChar = total.indexOf("\"");
            String title = total.substring(0, nextChar);
            total = total.substring(nextChar);
            total = total.substring(total.indexOf("year\":") + 6);
            int year = Integer.parseInt(total.substring(0, 4));
            total = total.substring(4);
            movies.add(new Movie(title, year));
            if (total.indexOf("title\":\"") == -1 || total.indexOf("year\":") == -1) {
                break;
            }
        }
        return movies.toArray(new Movie[0]);
    }


}
