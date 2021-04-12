package webshop.base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import webshop.data.DataProviders;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

@ContextConfiguration(locations = {"classpath:application-context-tests.xml"})
@Listeners({TestListener.class})
public class BaseTest extends AbstractTestNGSpringContextTests {

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver driver;

    @Value("${env}")
    public String env;

    @Value("${browser}")
    public String browser;

    @Value("${baseURL}")
    public String baseURL;

    @Value("${baseCountry}")
    public String baseCountry;

    @BeforeSuite(alwaysRun=true)
    public void beforeSuite() throws Exception {
        super.springTestContextPrepareTestInstance();
        DataProviders.env = this.env;
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        addParamsDetailsForAllureReport("src/test/resources/AllureReportInfoParams.properties",
                "Environment=" + this.env.toUpperCase() +
                    "\nBrowserName=" + capabilities.getBrowserName().toUpperCase() +
                    "\nBrowserVersion=" + capabilities.getVersion() +
                    "\nPlatform=" + capabilities.getPlatform()
                );

        copyParamDetailsFileToAllureReport();
        clearFile("src/test/resources/AllureReportInfoParams.properties");
    }

    @BeforeMethod(alwaysRun = true)
    protected void setUp() {
        //Set parameters in BasePage to be used in page classes
        BasePage.env = env;
        BasePage.browser = browser;
        BasePage.baseURL = baseURL;
        BasePage.baseCountry = baseCountry;

        //Instantiate driver and lunch browser
        driver = BrowserFactory.getDriver(browser);
        driver.manage().window().maximize();

        BasePage.webDriver = driver;
    }

    @AfterMethod(alwaysRun = true)
    protected void tearDown(Method method) {
        driver.quit();
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }
    
    private void addParamsDetailsForAllureReport(String filename, String value) {
        try (FileWriter file = new FileWriter(filename, false)) {
            file.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyParamDetailsFileToAllureReport() {
        File source = new File("src/test/resources/AllureReportInfoParams.properties");
        File destination = new File("target/allure-results/environment.properties");
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFile(String filename) {
        try {
            new FileWriter(filename, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

