package com.github.trentmenard.helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private final String title;
    private String maturityRating;
    private double budget;
    private double revenue;
    private double profit;
    private Date releaseDate;
    private final List<String> genres = new ArrayList<>();
    private final List<Director> directors = new ArrayList<>();
    private final int rank;
    private final URL url;

    public Movie(String title, int rank, URL url) {
        this.title = title;
        this.rank = rank;
        this.url = url;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public int getRank() {
        return rank;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", rank=" + rank +
                ", url=" + url +
                '}';
    }
}
