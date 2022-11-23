package com.github.trentmenard;

import java.net.URL;

public class Movie {
    String title;
    int rank;
    URL url;

    public Movie(String title, int rank, URL url) {
        this.title = title;
        this.rank = rank;
        this.url = url;
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
