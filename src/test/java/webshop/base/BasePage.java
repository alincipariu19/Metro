package webshop.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.SoftAssert;
import java.util.Random;
import static io.qameta.allure.Allure.step;

public class BasePage {

    public static String env;
    public static String browser;
    public static String baseURL;
    public static String baseCountry;
    public WebDriver driver;
    public static WebDriver webDriver;
    public int WAIT_TIME_OUT_IN_SECONDS = 30;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        loadElements();
    }

    public void loadElements() {
        PageFactory.initElements(driver, this);
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public void clickElement(WebElement element, String elementName) {
        step("Click element : \"" + elementName + "\"");
        try {
            waitUntilClickable(element);
        } catch (Exception e) {
        }

        element.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitUntilVisible(WebElement element) {
        new WebDriverWait(driver, WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilClickable(WebElement element) {
        new WebDriverWait(driver, WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForSpinnerToDisappear(int waitTimeInSeconds) {
        boolean condition = true;
        int i = 0;
        while (condition && i < waitTimeInSeconds) {
            WebElement spinner = null;
            try {
//                spinner = driver.findElement(By.xpath("//div[contains(@class,'loadingAnimationContainer')]"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            condition = isElementDisplayed(spinner);

            System.out.println("wait: " + i);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitUntilElemVisible(WebElement element, int waitTimeInSeconds) {
        boolean condition = false;
        int i = 0;
        while (!condition && i < waitTimeInSeconds) {

            condition = isElementDisplayed(element);

            System.out.println("wait: " + i);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void jsWaitForPageToLoad(int timeOutInSeconds) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        String jsCommand = "return document.readyState";

        // Validate readyState before doing any waits
        if (js.executeScript(jsCommand).toString().equals("complete")) {
            return;
        }

        for (int i = 0; i < timeOutInSeconds; i++) {
            if (js.executeScript(jsCommand).toString().equals("complete")) {
                break;
            }
            Thread.sleep(1000);
        }
    }

    public static int getRandomNumber(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }
}
