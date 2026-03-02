package base;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public static WebDriver driver;
    public Properties prop;

    @BeforeMethod
    public void setup() throws IOException {

        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/src/main/resources/config.properties");
        prop.load(fis);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
      
    }
    public String captureScreenshot(String testName) throws IOException {
    	TakesScreenshot ts=(TakesScreenshot) driver;
        // 1. Screenshot interface ka use karke source file capture karein
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        String timestamp = new java.text.SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String destinationPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + testName + "_" + System.currentTimeMillis() + ".png";
        System.out.println("Driver Check:"+driver);
        try {
        	System.out.println("DEBUG:Screenshot function call hua hai!");
            File finalDestination = new File(destinationPath);
            File folder = new File(System.getProperty("user.dir") + "/screenshots");
            
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 2. Yaha 'source' variable use hoga jo humne upar banaya hai
            org.apache.commons.io.FileUtils.copyFile(source, finalDestination);
            System.out.println("Screenshot saved at: " + destinationPath);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinationPath;
    }
    @AfterMethod
    public void tearDown() {
    	if(driver !=null) {
    		driver.quit();
    		System.out.println("Browser closed successfully");
    	}
    }
}