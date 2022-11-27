package com.github.trentmenard.helpers;

import java.util.List;

public abstract class Person {
    private final String name;
    private String birthday;
    private String birthplace;
    private String bio;
    private int age;
    private List<Movie> knownFor;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getBio() {
        return bio;
    }

    public int getAge() {
        return age;
    }

    public List<Movie> getKnownFor() {
        return knownFor;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setKnownFor(List<Movie> knownFor) {
        this.knownFor = knownFor;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", birthplace='" + birthplace + '\'' +
                ", bio='" + bio + '\'' +
                ", age=" + age +
                ", knownFor=" + knownFor +
                '}';
    }
}
