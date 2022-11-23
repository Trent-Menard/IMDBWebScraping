package com.github.trentmenard;

public class Main {
    public static void main(String[] args) {

        // Connect to proxy to avoid IP ban
        System.setProperty("http.proxyHost", "172.105.216.60");
        System.setProperty("http.proxyPort", "443");

//        GenreScrape genreScrape = new GenreScrape();
//        genreScrape.getMovies().forEach(System.out::println);

        MovieScrape movieScrape = new MovieScrape("https://www.imdb.com/title/tt9114286/?ref_=adv_li_tt");
    }
}