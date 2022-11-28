package com.github.trentmenard.helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private final String title;
    private String maturityRating;
    private int budget;
    private int revenue;
    private int profit;
    private String releaseDate;
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

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public void setReleaseDate(String releaseDate) {
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

    public String getMaturityRating() {
        return maturityRating;
    }

    public int getBudget() {
        return budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getProfit() {
        return profit;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", maturityRating='" + maturityRating + '\'' +
                ", budget=" + budget +
                ", revenue=" + revenue +
                ", profit=" + profit +
                ", releaseDate='" + releaseDate + '\'' +
                ", genres=" + genres +
                ", directors=" + directors +
                ", rank=" + rank +
                ", url=" + url +
                '}';
    }
}