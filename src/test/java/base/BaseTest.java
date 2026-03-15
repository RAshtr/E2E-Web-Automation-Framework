package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public static WebDriver driver;
    public Properties prop;

    // 1. Centralized Driver Initialization Logic
    public WebDriver initializeDriver() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") 
                + "/src/main/resources/config.properties");
        prop.load(fis);
        
        // Maven command se browser name lena, nahi toh properties file se
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } 
        // Yaha aap else if karke firefox/edge ka logic future mein dal sakte hain

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod
    public void setup() throws IOException {
        // Ab hum centralized method use karenge
        driver = initializeDriver();
    }

    // 2. Screenshot Utility jo aapne pehle banayi thi
    public String captureScreenshot(String testName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String destinationPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + testName + "_" + timestamp + ".png";
        
        try {
            File finalDestination = new File(destinationPath);
            File folder = new File(System.getProperty("user.dir") + "/screenshots");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileUtils.copyFile(source, finalDestination);
            System.out.println("Screenshot saved at: " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinationPath;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully");
        }
    }
}