package com.github.trentmenard.helpers;

public class Director extends Person {
    private final String url;

    public Director(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return super.toString() + " & Director{" +
                "url=" + url +
                '}';
    }
}