package com.github.trentmenard;

import com.github.trentmenard.helpers.Director;
import com.jauntium.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoviePage {
    public MoviePage(String title, String url) {
        this.title = title;
        this.url = url;
    }

    private String title;
    private String url;
    private String releaseDate = "null";
    private double boxOfficeGross = 0;
    private double boxOfficeBudget = 0;
    private List<String> genres = new ArrayList<>();

    private By maturityRatingSelector = By.cssSelector("[data-testid = 'hero-title-block__metadata']");

    private String maturityRating = "null";
    private final By releaseDateSelector = By.cssSelector("[data-testid = 'title-details-releasedate']");
    private final By boxOfficeSelector = By.cssSelector("[data-testid = 'title-boxoffice-cumulativeworldwidegross']");
    private final By boxOfficeBudgetSelector = By.cssSelector("[data-testid = 'title-boxoffice-budget']");
    private final By genresSelector = By.xpath("//li[@data-testid=\"storyline-genres\"]");
    private List<Director> directors = new ArrayList<>();
    private final By directorsSelector = By.cssSelector("li.ipc-metadata-list__item");

    public void scrape(WebDriver driver){

        try {
            releaseDate = driver.findElement(releaseDateSelector).getText().replaceAll("Release date", "").replaceAll(" \\(.*\\)", "").strip();

            DateTimeFormatter dtfFrom = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            DateTimeFormatter dtfTo = DateTimeFormatter.ofPattern("d-MMM-yyyy");
            TemporalAccessor tmp = dtfFrom.parse(releaseDate);
            releaseDate = dtfTo.format(tmp).toUpperCase();
        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: releaseDate.");}

        try {
            boxOfficeGross = Double.parseDouble(driver.findElement(boxOfficeSelector).getText().replaceAll("\\D", "").strip() + "D");
        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: boxOfficeGross.");}

        try {
            boxOfficeBudget = Double.parseDouble(driver.findElement(boxOfficeBudgetSelector).getText().replaceAll("\\D", "").strip() + "D");
        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: boxOfficeBudget.");}

        try {
            driver.findElement(genresSelector)
                    .findElements(By.tagName("a"))
                    .forEach(s -> genres.add(s.getText()));
        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: genres.");}

        try {
            driver.findElement(directorsSelector)
                    .findElements(By.tagName("a"))
                    .forEach(s -> directors.add(new Director(s.getText(), s.getAttribute("href"))));
        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: directors.");}

        try {
            List<String> tmp = new ArrayList<>();
            driver.findElement(maturityRatingSelector)
                    .findElements(By.cssSelector("a"))
                    .forEach(s -> tmp.add(s.getText()));

            if (tmp.size() > 1)
                maturityRating = tmp.get(1);
            else
                Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: maturityRating.");

        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: maturityRating.");}
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getBoxOfficeGross() {
        return boxOfficeGross;
    }

    public double getBoxOfficeBudget() {
        return boxOfficeBudget;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    @Override
    public String toString() {
        return "MoviePage{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", boxOfficeGross=" + boxOfficeGross +
                ", boxOfficeBudget=" + boxOfficeBudget +
                ", genres=" + genres +
                ", maturityRating='" + maturityRating + '\'' +
                ", directors=" + directors +
                '}';
    }
}