package com.github.trentmenard;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AutobiographyPage {
    private String bioRedirect;
    private String bio;
    private String birthday;
    private String birthplace;
    private final By bioRedirectSelector = By.cssSelector("a[class^=\"ipc-overflowText-overlay\"]");
    private final By bioSelector = By.xpath("/html/body/div[2]/div/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p[1]");
    private final By birthdaySelector = By.xpath("//time");
    private final By birthplaceSelector = By.xpath("//a[contains(@href, \"place\")]");
    public AutobiographyPage(WebDriver driver) {

        try {
            bioRedirect = driver.findElement(bioRedirectSelector).getAttribute("href");
        }catch(NoSuchElementException ex){
            Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: bioRedirect.");}
    }

    public void scrape(WebDriver driver){
        try {
            bio = driver.findElement(bioSelector).getText();

            // Bio shouldn't exceed 300 chars; substring and concat ellipses.
            if (bio.length() >= 297){
                bio = bio.substring(0, 297);
                bio += "...";
            }
            // Escape single quote with another single quote.
            bio = bio.replaceAll("'", "''");
            bio = bio.replaceAll(" See full bio »", "");

        }catch(NoSuchElementException ex){
            Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: bioSelector.");}

        try {
            birthday = driver.findElement(birthdaySelector).getText();
        }catch(NoSuchElementException ex){
            Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: birthdaySelector.");}

        try {
            birthplace = driver.findElement(birthplaceSelector).getText();
        }catch(NoSuchElementException ex){
            Logger.getAnonymousLogger().log(Level.WARNING,"Failed to find elements for: birthplaceSelector.");}
    }

    public String getBioRedirect() {
        return bioRedirect;
    }

    public String getBio() {
        return bio;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    @Override
    public String toString() {
        return "AutobiographyPage{" +
                "bioRedirect='" + bioRedirect + '\'' +
                ", bio='" + bio + '\'' +
                ", birthday='" + birthday + '\'' +
                ", birthplace='" + birthplace + '\'' +
                '}';
    }
}