package webshop.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import webshop.pages.ConsentPage;

public class PageLoader extends BasePage {

    public PageLoader(WebDriver driver) {
        super(driver);
    }

    @Step("Load PDP with specific product: {1}")
    public static void loadPDPWithSpecificProduct(String country, String productCode) throws InterruptedException {
        webDriver.navigate().to(baseURL + country + "/?DEEP=" + productCode);
        jsWaitForPageToLoad(5);
        ConsentPage consentPage = new ConsentPage(webDriver);
        consentPage.closeConsentBanner();
    }
}