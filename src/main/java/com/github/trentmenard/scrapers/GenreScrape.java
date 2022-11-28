package com.github.trentmenard.scrapers;

import com.github.trentmenard.helpers.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreScrape {
    final List<Movie> movies = new ArrayList<>();
    final String baseURL = "https://www.imdb.com";
    public GenreScrape() {

        // Scrape Top 50 Sci-Fi Films.
        try {
            Document page = Jsoup.connect(baseURL + "/search/title/?genres=sci-fi&title_type=feature&explore=genres").get();
            Elements movieElements = page.getElementsByClass("lister-item-header");

            movieElements.forEach(e ->
            {
                String title = null;
                int rank = -1;
                URL url;
                try {url = new URL("https://www.imdb.com/");} catch (MalformedURLException ex) {throw new RuntimeException(ex);}

                if (!(e.selectFirst("a").text().isEmpty()))
                    title = e.selectFirst("a").text();
                else
                    Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find element 'a'; set title failed.");

                if (!(e.getElementsByTag("span").eachText().get(0)).isEmpty())
                    rank = Integer.parseInt(e.getElementsByTag("span").eachText().get(0).replaceAll("\\.", ""));
                else
                    Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find class 'span'; set rank failed.");

                if ((e.selectFirst("a[href]") != null) && (!(e.selectFirst("a[href]").attr("href").isEmpty())))
                    try {url = new URL(baseURL + e.selectFirst("a[href]").attr("href"));} catch (MalformedURLException ex) {throw new RuntimeException(ex);}
                else
                    Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find element 'a[href]'; set URL failed.");

                Movie m = new Movie(title, rank, url);
                movies.add(m);
            });

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
