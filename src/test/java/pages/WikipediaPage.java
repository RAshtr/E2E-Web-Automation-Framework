package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WikipediaPage {

    WebDriver driver;

    // Constructor
    public WikipediaPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locator
    By searchBox = By.id("searchInput");
    By searchButton = By.xpath("//button[@type='submit']");

    // Action Method
    public void search(String text) {
        driver.findElement(searchBox).sendKeys(text);
        driver.findElement(searchButton).click();
    }
}


