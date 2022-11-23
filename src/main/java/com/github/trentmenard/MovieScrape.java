package com.github.trentmenard;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class MovieScrape {
    final List<Movie> movies = new ArrayList<>();
    final String baseURL = "https://www.imdb.com";
    public MovieScrape(String url) {

        // Scrape Top 50 Sci-Fi Films.
        try {
            Document page = Jsoup.connect(url).get();
            Elements movieElements = page.getElementsByClass("ipc-metadata-list-item__label--btn");
//            Elements movieElements = page.getElementsByClass("ipc-metadata-list-item__list-content-item--link");
//            Set<Director> directors = new HashSet<>();

            movieElements.forEach(e ->
            {
                if (e.text().equalsIgnoreCase("Director")){
                    System.out.println(e.nextElementSibling());
                }

//                getElementsMatchingText("Director")

//                System.out.println(e);
//                Director director = new Director();

//                String href = movieElements.attr("href");
//                System.out.println(e);
//                String title = "UNKNOWN";
//                int rank = -1;
//                URL url = null;
//                try {url = new URL("https://www.imdb.com/");} catch (MalformedURLException ex) {throw new RuntimeException(ex);}
//
//                if (!(e.selectFirst("a").text().isEmpty()))
//                    title = e.selectFirst("a").text();
//                else
//                    Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find element 'a'; set title failed.");
//
//                if (!(e.getElementsByTag("span").eachText().get(0)).isEmpty())
//                    rank = Integer.parseInt(e.getElementsByTag("span").eachText().get(0).replaceAll("\\.", ""));
//                else
//                    Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find class 'span'; set rank failed.");
//
//                if ((e.selectFirst("a[href]") != null) && (!(e.selectFirst("a[href]").attr("href").isEmpty())))
//                    try {url = new URL(baseURL + e.selectFirst("a[href]").attr("href"));} catch (MalformedURLException ex) {throw new RuntimeException(ex);}
//                else
//                    Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find element 'a[href]'; set URL failed.");
//
//                Movie m = new Movie(title, rank, url);
//                movies.add(m);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //            Document page = Jsoup.connect("https://www.imdb.com/title/tt9114286").get();
//            Elements aHrefs = page.getElementsByClass("ipc-metadata-list__item");
//            Elements movieElements = page.getElementsByClass("lister-item-index");
    }

    public List<Movie> getMovies() {
        return movies;
    }


}
