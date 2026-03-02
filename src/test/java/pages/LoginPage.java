package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    private By errorMessage=By.id("flash");
    public String getErrorMessage() {
    	return driver.findElement(errorMessage).getText();
    }

    // Locators
    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By loginButton = By.cssSelector("button[type='submit']");
    By successMessage = By.id("flash");

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    
}