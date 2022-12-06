package com.github.trentmenard;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static WebDriver driver;

    public static void main(String[] args) {

        setup();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://www.imdb.com/search/title/?genres=sci-fi&title_type=feature&explore=genres");

        System.out.println("Gathering movies.");
        GenrePage genrePage = new GenrePage(driver);
        System.out.println("Found " + genrePage.getMovieList().size() + " results!");
        System.out.println();

        StringBuilder sb = new StringBuilder();
        AtomicInteger progress = new AtomicInteger(1);

        System.out.println("Gathering movie data.");
        genrePage.getMovieList().forEach(m -> {
            System.out.println("Processing: " + m.getTitle());
            System.out.println("Progress: " + progress.get() + " / " + genrePage.getMovieList().size());
            System.out.println();

            driver.get(m.getUrl());
            m.scrape(driver);

            progress.getAndIncrement();
        });

        System.out.println();
        progress.set(0);
        System.out.println("Gathering autobiographies.");

        genrePage.getMovieList().forEach(m -> m.getDirectors().forEach(d -> {

            System.out.println("Processing director: " + d.getName() + " for " + m.getTitle());
            System.out.println("Progress: " + (progress.get() + 1) + " / " + genrePage.getMovieList().size());
            System.out.println();

            driver.get(d.getUrl());
            AutobiographyPage autobiographyPage = new AutobiographyPage(driver);
            driver.get(autobiographyPage.getBioRedirect());
            autobiographyPage.scrape(driver);

            d.setBio(autobiographyPage);
            d.setBirthday(autobiographyPage.getBirthday());
            d.setBirthplace(autobiographyPage.getBirthplace());

            progress.getAndIncrement();
        }));

        genrePage.getMovieList().forEach(m -> {

            sb.append("INSERT INTO Movie\n")
                    .append("VALUES('")
                    .append(m.getTitle())
                    .append("','")
                    .append(m.getReleaseDate())
                    .append("','")
                    .append(m.getMaturityRating())
                    .append("',")
                    .append(Math.round(m.getBoxOfficeGross()))
                    .append(",")
                    .append(Math.round(m.getBoxOfficeBudget()))
                    .append(");\n\n");

            m.getGenres().forEach( g -> {
                sb.append("INSERT INTO Genre\n");
                sb.append("VALUES('")
                        .append(m.getTitle())
                        .append("','")
                        .append(m.getReleaseDate())
                        .append("','")
                        .append(g)
                        .append("');\n\n");
            });

            m.getDirectors().forEach(d -> {
                sb.append("INSERT INTO Person\n")
                        .append("VALUES('")
                        .append(d.getName())
                        .append("','")
                        .append(d.getBirthday())
                        .append("','")
                        .append(d.getBirthplace())
                        .append("','")
                        .append(d.getBio().getBio())
                        .append("');\n\n");

                sb.append("INSERT INTO Director('")
                        .append(d.getName())
                        .append("');\n\n");

                sb.append("INSERT INTO Directed\n")
                        .append("VALUES('")
                        .append(m.getTitle())
                        .append("','")
                        .append(m.getReleaseDate())
                        .append("','")
                        .append(d.getName())
                        .append("');\n");
            });

            sb.append("\n\n");

        });

        try {
            String data = String.valueOf(sb);

            FileWriter file = new FileWriter("IMDB.SQL");

            BufferedWriter output = new BufferedWriter(file);

            output.write(data);

            output.flush();

            System.out.println("Done!");

            output.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    private static void setup(){
        // Connect to proxy to avoid IP ban
        System.setProperty("http.proxyHost", "172.105.216.60");
        System.setProperty("http.proxyPort", "443");

        // Setup Edge WebDriver
        driver = WebDriverManager.edgedriver().create();
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}