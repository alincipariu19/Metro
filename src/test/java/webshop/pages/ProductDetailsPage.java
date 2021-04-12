package webshop.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import webshop.base.BasePage;
import static io.qameta.allure.Allure.step;

public class ProductDetailsPage extends BasePage {

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.addToCart button")
    private WebElement shoppingCartButton;

    @FindBy(css = "div.buttonsContainer a.btn.btn-right.btnToCheckout")
    private WebElement shoppingCartButtonFromConfirmationOverlay;

    @FindBy(css = "div.productID")
    public WebElement articleNumberFromPDP;

    @Step("Add product to Shopping Cart")
    public void addProductToShoppingCart() {
        waitUntilVisible(shoppingCartButton);
        clickElement(shoppingCartButton, "Shopping Cart button");
        waitUntilVisible(shoppingCartButtonFromConfirmationOverlay);
    }

    @Step("Click on To The Basket Of Goods Button")
    public void goToShoppingCartButtonFromConfirmationPopup() {
        waitUntilVisible(shoppingCartButtonFromConfirmationOverlay);
        clickElement(shoppingCartButtonFromConfirmationOverlay, "To The Basket Of Goods button");
    }

    @Step("Verify PDP is displayed")
    public void verifyProductDetailsPageIsDisplayed() throws InterruptedException {
        jsWaitForPageToLoad(10);
        step("Check URL contains the article number from PDP");
        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains(articleNumberFromPDP.getText().replace("Artikelnummer: ", "").toLowerCase()), "URL doesn't contain the article number from PDP");

    }
}
