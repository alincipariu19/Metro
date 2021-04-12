package webshop.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class BrowserFactory {

    public static WebDriver getDriver(String browser) {
        WebDriver driver = null;
        String userAgentString = " 1sZN24191NkmvT7siwuvx";
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-gpu");
        options.addArguments("--verbose");
        options.addArguments("--whitelisted-ips");
        options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36" + userAgentString);
//                options.addArguments("--headless");
        options.setCapability("idleTimeout", 300);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        return driver;
    }

}
