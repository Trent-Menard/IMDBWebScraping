package com.github.trentmenard.helpers;

import java.net.URL;

public class Director extends Person {
    private final URL url;

    public Director(String name, URL url) {
        super(name);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return super.toString() + " & Director{" +
                "url=" + url +
                '}';
    }
}