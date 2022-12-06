package com.github.trentmenard;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// page_url = https://www.imdb.com/search/title/?genres=sci-fi&title_type=feature&explore=genres
public class GenrePage {

    private final List<MoviePage> movieList = new ArrayList<>();

    public GenrePage(WebDriver driver){

        By movieListSelector = By.cssSelector("h3.lister-item-header");

        try {
            List<WebElement> tmpLst = driver.findElements(movieListSelector)
                    .stream()/*.limit(2)*/
                    .map(s -> s.findElement(By.cssSelector("a")))
                    .toList();

            tmpLst.forEach(s -> movieList.add(new MoviePage(s.getText(), s.getAttribute("href"))));

        }catch(NoSuchElementException ex){Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: movieList.");}
    }

    public List<MoviePage> getMovieList() {
        return movieList;
    }

    @Override
    public String toString() {
        return "GenrePage{" +
                "movieList=" + movieList +
                '}';
    }
}