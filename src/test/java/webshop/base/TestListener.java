package webshop.base;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BaseTest implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG (WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static  String saveTextLog (String message) {
        return message;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("TEST STARTED: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

        try {
            //Get driver from BaseTest and assign to local webdriver available
            Object testClass = iTestResult.getInstance();
            WebDriver driver = ((BaseTest) testClass).getDriver();

            //Allure ScreenShotRobot and SaveTestLog
            if (driver instanceof WebDriver) {
                System.out.println("Screenshot captured for test case" + getTestMethodName(iTestResult));
                try {
                    saveScreenshotPNG(driver);
                } catch (Exception e) {
                    System.out.println("Webdriver: " + driver);
                    System.out.println("Driver error when trying to take screenshot: " + e);
                }
            }

            //Save a log on Allure
            saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
