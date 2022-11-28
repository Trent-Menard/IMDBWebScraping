package com.github.trentmenard;

import com.github.trentmenard.helpers.Movie;
import com.github.trentmenard.helpers.Person;
import com.github.trentmenard.scrapers.DirectorScrape;
import com.github.trentmenard.scrapers.GenreScrape;
import com.github.trentmenard.scrapers.MovieScrape;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {

        // Connect to proxy to avoid IP ban
        System.setProperty("http.proxyHost", "172.105.216.60");
        System.setProperty("http.proxyPort", "443");

        GenreScrape genreScrape = new GenreScrape();
        StringBuilder sb = new StringBuilder();

        AtomicInteger i = new AtomicInteger(1);
        genreScrape.getMovies().forEach(gs -> {
            DirectorScrape directorScrape = new DirectorScrape(gs);
            System.out.println(i.get() + " / 50");
            System.out.println(gs.getTitle() + " directed by:\n" + directorScrape.getDirectors());
            i.getAndIncrement();
            System.out.println();

            // Not good; director needs to be converted to complex attribute or discard other writers?
            sb.append("INSERT INTO Person\n")
                    .append("VALUES('")
                    .append(directorScrape.getDirectors().stream().map(Person::getName).toList())
                    .append("','")
                    .append(directorScrape.getDirectors().stream().map(Person::getBirthday).toList())
                    .append("','")
                    .append(directorScrape.getDirectors().stream().map(Person::getBirthplace).toList())
                    .append("','")
                    .append(directorScrape.getDirectors().stream().map(Person::getBio).toList())
                    .append("');\n\n");


            sb.append("INSERT INTO Director('")
                    .append(directorScrape.getDirectors().stream().map(Person::getName).toList())
                    .append("');\n\n");


            sb.append("INSERT INTO Directed\n")
                    .append("VALUES('")
                    .append(gs.getTitle())
                    .append("','")
                    .append(gs.getReleaseDate())
                    .append("','")
                    .append(directorScrape.getDirectors().stream().map(Person::getName).toList())
                    .append("');\n\n");


            sb.append("INSERT INTO Movie\n")
                    .append("VALUES('")
                    .append(gs.getTitle())
                    .append("','")
                    .append(gs.getReleaseDate())
                    .append("','")
                    .append(gs.getMaturityRating())
                    .append("',")
                    .append(gs.getRevenue())
                    .append(",")
                    .append(gs.getBudget())
                    .append(");\n\n\n");
        });

        try {
            String data = String.valueOf(sb);

            FileWriter file = new FileWriter("IMDB.txt");

            BufferedWriter output = new BufferedWriter(file);

            output.write(data);

            output.flush();

            System.out.println("Done!");

            output.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}