package com.github.trentmenard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = https://www.imdb.com/search/title/?genres=sci-fi&title_type=feature&explore=genres
public class tmp {

    @FindBy(css = "[data-testid = 'hero-title-block__metadata']")
    public WebElement heroTitleBlockMetadata2;

    @FindBy(xpath = "//li[.//a[@href=\"/title/\"]]")
    public WebElement ipcInlineListItem;

    public tmp(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}