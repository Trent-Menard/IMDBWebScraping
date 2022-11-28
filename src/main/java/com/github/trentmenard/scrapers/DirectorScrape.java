package com.github.trentmenard.scrapers;

import com.github.trentmenard.helpers.Director;
import com.github.trentmenard.helpers.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectorScrape {
    private final Document moviePage;
    private Document directorsPage;
    private final List<Director> directors = new ArrayList<>();
    public DirectorScrape(Movie movie) {
        try {
            // Connect to movie page.
            moviePage = Jsoup.connect(movie.getUrl().toString()).get();

            // Scrape & create List<Director> from Director's name & url.
            nameAndURL();
        } catch (IOException e) {throw new RuntimeException(e);}

        this.directors.forEach(d -> {
            try {
                // Connect to director's page.
                directorsPage = Jsoup.connect(d.getUrl().toString()).get();

                // Scrape necessary data from director's page.
                directorPage(d);

            } catch (IOException e) {throw new RuntimeException(e);}
        });
    }

    private void nameAndURL(){
        Element targetPreCheck = moviePage.getElementsContainingOwnText("Director").first();
        Elements targets;

        if (targetPreCheck != null && !(targetPreCheck.text().isEmpty())){
            targetPreCheck = targetPreCheck.nextElementSibling();

            if (targetPreCheck != null){
                targets = targetPreCheck.getElementsByAttribute("href");
                targets.forEach(e -> {
                    String directorName = e.text();
                    String baseURL = "https://www.imdb.com";
                    String tmp = baseURL + e.attr("href");
                    try {
                        URL directorURL = new URL(tmp);
                        Director director = new Director(directorName, directorURL);
                        this.directors.add(director);
                    } catch (MalformedURLException ex) {throw new RuntimeException(ex);}
                });
            } else
                Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find " + "Director" + "'s 'a href'; set " + "Director" + " name/url failed.");
        }
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find " + "Director" + " element; set " + "Director" + " name/url failed.");
    }

    private void directorPage(Director director){

        Elements elem2 = directorsPage.getElementsByClass("name-trivia-bio-text");
        String bio = null;
        String birthday = null;
        String birthplace = null;
        int age = -1;

        if (!elem2.isEmpty()){
            bio = elem2.text();
            // Bio shouldn't exceed 300 chars; substring and concat ellipses.
            if (bio.length() >= 297){
                bio = bio.substring(0, 297);
                bio += "...";
            }
            // Escape single quote with another single quote.
            bio = bio.replaceAll("'", "''");
            bio = bio.replaceAll(" See full bio »", "");
        }
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find class 'name-trivia-bio-text'; set bio failed.");

        Elements time = directorsPage.getElementsByTag("time");

        if (!time.isEmpty()){
            birthday = time.text();
            birthplace = time.next().text();

            // Extract last 4 digits (birth year)
            String yyyy_ext = birthday.substring((birthday.length() - 4));
            int yyyy = Integer.parseInt(yyyy_ext);
            age = YearMonth.now().getYear() - yyyy;
        }
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find tag 'time'; set birthday/place failed.");

        director.setBio(bio);
        director.setBirthday(birthday);
        director.setBirthplace(birthplace);
        director.setAge(age);
    }

    public List<Director> getDirectors() {
        return directors;
    }
}