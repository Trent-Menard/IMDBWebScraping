package com.github.trentmenard.scrapers;

import com.github.trentmenard.helpers.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieScrape {
    private final Document moviePage;
    private final Movie movie;
    public MovieScrape(Movie movie) {
        this.movie = movie;

        try {
            // Connect to movie page.
            moviePage = Jsoup.connect(movie.getUrl().toString()).get();

            movieDetails();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private void movieDetails(){

        String releaseDate = null;
        String rating = null;
        int budget = -1;
        int revenue = -1;

        Elements target = moviePage.getElementsMatchingOwnText("Release date").next();
        // Remove anything in parentheses
        if (!target.text().isEmpty())
             releaseDate = target.text().replaceAll(" \\(.*\\)", "");
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find matching 'Release date' element; set release date failed.");


        target = moviePage.getElementsMatchingOwnText("Budget").next();
        if (!target.text().isEmpty())
            // Remove all non-digits
            budget = Integer.parseInt(target.text().replaceAll("\\D", ""));
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find matching 'Budget' element; set budget failed.");


        target = moviePage.getElementsMatchingOwnText("Gross worldwide").next();
        if (!target.text().isEmpty())
            // Remove all non-digits
            revenue = Integer.parseInt(target.text().replaceAll("\\D", ""));
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find matching 'Gross worldwide' element; set Gross worldwide failed.");


        target = moviePage.selectXpath("/html/body/div[2]/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/div/ul/li[2]/span");
        if (!target.text().isEmpty())
            rating = target.text();
        else
            Logger.getAnonymousLogger().log(Level.WARNING,"Unable to find matching 'Rating' element; set rating failed.");

        this.movie.setReleaseDate(releaseDate);
        this.movie.setMaturityRating(rating);
        this.movie.setBudget(budget);

        this.movie.setRevenue(revenue);
        int profit = this.movie.getRevenue() - this.movie.getBudget();
        this.movie.setProfit(profit);
    }

    @Override
    public String toString() {
        return "MovieScrape{" +
                "movie=" + movie +
                '}';
    }
}