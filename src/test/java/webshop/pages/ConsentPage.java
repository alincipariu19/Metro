package webshop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webshop.base.BasePage;

public class ConsentPage extends BasePage {

    public ConsentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "iframe[id*=\"sp_message_iframe\"]")
    public WebElement iframe;

    @FindBy(css = "button[title='Zustimmen']")
    public WebElement agreeButtonFromConsentBanner;

    public void closeConsentBanner() {
        waitUntilElemVisible(iframe, 5);
        if(isElementDisplayed(iframe)) {
            driver.switchTo().frame(iframe);
            clickElement(agreeButtonFromConsentBanner, "Agree button from Consent banner");
            driver.switchTo().parentFrame();
        }
    }
}
