package com.github.trentmenard;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        System.setProperty("http.proxyHost", "172.105.216.60");
        System.setProperty("http.proxyPort", "443");

        final String baseURL = "https://www.imdb.com";
        final HashMap<String, String> movies = new HashMap<>();

        // Scrape Top 50 Sci-Fi Films.
        try {
            Document page = Jsoup.connect(baseURL + "/search/title/?genres=sci-fi&title_type=feature&explore=genres").get();

            Elements movieElements = page.getElementsByClass("lister-item-content");

            movieElements.forEach(e ->
            {
                System.out.println("Rank: " + e.getElementsByClass("lister-item-index").text());
                System.out.println("Title: " + e.selectFirst("a").text());
                System.out.println("Href:" + e.selectFirst("a[href]").attr("href"));
                System.out.println("Full URL: " + baseURL + e.selectFirst("a[href]").attr("href"));
                System.out.println();

                movies.put(e.selectFirst("a").text(), baseURL + e.selectFirst("a[href]").attr("href"));
            });

        } catch (IOException e) {throw new RuntimeException(e);}

        //            Document page = Jsoup.connect("https://www.imdb.com/title/tt9114286").get();
//            Elements aHrefs = page.getElementsByClass("ipc-metadata-list__item");
//            Elements movieElements = page.getElementsByClass("lister-item-index");
    }
}