package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

@Listeners(utils.Listeners.class) // Ye line TestNG ko force karegi listener use karne ke liye
public class LoginTest extends BaseTest {


    @Test
    public void validLoginTest() {

        driver.get("https://the-internet.herokuapp.com/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("tomsmith", "WrongPassword123!");

        String message = loginPage.getSuccessMessage();
        String lowerMessage = message.toLowerCase(); // Ye line yahan zaroori hai

        // Ab yahan variable banaiye
        boolean isErrorDisplayed = lowerMessage.contains("invalid") || 
                                   lowerMessage.contains("username") || 
                                   lowerMessage.contains("password") || 
                                   lowerMessage.contains("required") || 
                                   lowerMessage.contains("empty");

        Assert.assertTrue(isErrorDisplayed, "Expected an error message but got: " + message);
    }
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][] {
            {"wrongUser", "SuperSecretPassword!"}, // Set 1
            {"tomsmith", "wrongPassword"},          // Set 2
            {"", ""},
            {" ' OR '1'='1","password123"},
            {"tomsmith",""},
            {"testUser12345678901234567890","SuperSecretPassword!"}// Set 3 (Empty fields check)
        };
    }

    @Test(dataProvider = "loginData")
    public void invalidLoginTest(String user, String pass) {
        driver.get("https://the-internet.herokuapp.com/login");
        LoginPage loginPage = new LoginPage(driver);
        
        // Login perform karein
        loginPage.login(user, pass);
        
        // Message nikalne se pehle 2 second ka wait add kar dena (Agar NoSuchElement aaye)
        String message = loginPage.getErrorMessage();
        
        // YAHAN HAI FIX: Hum check karenge ki message mein "invalid" ya "required" jaisa keyword hai ya nahi
        // Bajaye pura message match karne ke, sirf keyword check karna DDT mein safe hota hai
        String lowerMessage = message.toLowerCase();

        boolean isErrorDisplayed = lowerMessage.contains("invalid") || 
                                   lowerMessage.contains("username") || 
                                   lowerMessage.contains("password") || 
                                   lowerMessage.contains("required") ||
                                   lowerMessage.contains("empty");
        Assert.assertTrue(isErrorDisplayed, "Expected an error message but got: " + message);
    }
}
