package javathehutt.buzz_movieselector.movie;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.kevinsawicki.http.HttpRequest;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * Created by Mohammed on 2/16/2016.
 */
public class RottenTomatoesJSON implements RottenTomatoes {
    private final int TIMEOUT = 200;
    @Override
    public Movie[] searchMovieByName(String name){
        HttpRequest h = makeRequestByName(name);
        return makeMovie(extractReader(h));
    };

    @Override
    public Movie[] recentDVD() {
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
            return HttpRequest.get(URL, true,
                    "apikey", KEY, "q", name);
        } catch (HttpRequest.HttpRequestException e) {
            return null;
        }
    }
    public HttpRequest makeRequestByGenre() {
        return null;
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
        String toProcess = scan.nextLine();
        /*while(scan.hasNext()) {

            String toProcess = scan.next();
            if(toProcess.equals("title")) {
                title = scan.next();
            }
            if(toProcess.equals("year")) {
                year = new Integer(scan.next());
            }
        }*/
        while(toProcess.length() != 0) {
            toProcess = toProcess.substring(toProcess.indexOf("title\":\"") + 8);
            int nextChar = toProcess.indexOf("\"");
            String title = toProcess.substring(0, nextChar);
            toProcess = toProcess.substring(nextChar);
            toProcess = toProcess.substring(toProcess.indexOf("year\":") + 6);
            int year = Integer.parseInt(toProcess.substring(0, 4));
            toProcess = toProcess.substring(4);
            movies.add(new Movie(title, year));
            if (toProcess.indexOf("title\":\"") == -1 || toProcess.indexOf("year\":") == -1) {
                break;
            }
        }
        return movies.toArray(new Movie[0]);
    }


}
