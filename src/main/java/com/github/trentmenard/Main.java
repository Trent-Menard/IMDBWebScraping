package com.github.trentmenard;

import com.github.trentmenard.scrapers.DirectorScrape;
import com.github.trentmenard.scrapers.GenreScrape;

public class Main {
    public static void main(String[] args) {

        // Connect to proxy to avoid IP ban
        System.setProperty("http.proxyHost", "172.105.216.60");
        System.setProperty("http.proxyPort", "443");

        GenreScrape genreScrape = new GenreScrape();

        genreScrape.getMovies().forEach(gs -> {
            DirectorScrape directorScrape = new DirectorScrape(gs);
            System.out.println(gs.getTitle() + " directed by:\n" + directorScrape.getDirectors());
            System.out.println();
        });

// "https://www.imdb.com/title/tt9114286/?ref_=adv_li_tt");
//        try {
//            // Connect to director's page.
////            Document directorsPage = Jsoup.connect("https://www.imdb.com/name/nm3363032/?ref_=tt_ov_dr").get();
//
//
//            // Inconsistent results
////            Elements v = directorsPage.select("section.ipc-page-section.ipc-page-section--base");
//
//            // Inconsistent results
////            Elements els = directorsPage.selectXpath("/html/body/div[2]/div/div[2]/div[3]/div[3]/div[3]/h2");
////            System.out.println(els.nextAll());
////            els.forEach(s -> System.out.println(s));
//
//            // Inconsistent results
////            System.out.println(directorsPage.selectXpath("/html/body/div[2]/main/div/section[1]/div/section/div/div[1]/div[4]/section[1]/div[1]/hgroup/h3").text());
////            System.out.println(directorsPage.selectXpath("/html/body/div[2]/main/div/section[1]/div/section/div/div[1]/div[4]/section[1]/div[1]/hgroup/h3").text());
////            System.out.println(e);
//
//        } catch (IOException e) {throw new RuntimeException(e);}
    }
}