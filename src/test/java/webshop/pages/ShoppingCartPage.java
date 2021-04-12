package webshop.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import webshop.base.BasePage;
import java.util.List;
import static io.qameta.allure.Allure.step;

public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    SoftAssert softAssert = new SoftAssert();

    @FindAll({
            @FindBy(css = "div.cartListWrapper.listindex div.cartItem"),
            @FindBy(css = "div.productsContainer article.cartItem")})
    public List<WebElement> productsList;

    @FindAll({
            @FindBy(css = "div[class='productEvents']"),
            @FindBy(css = "div.delete")})
    public List<WebElement> removeProductButtonsList;

    @FindAll({
            @FindBy(css = "div.cartEmptyInfo"),
            @FindBy(css = "div.cartEmptyMessage")})
    public WebElement emptyTitleForShoppingCart;

    @FindAll({
            @FindBy(css = "button.btn.btn-right.btn-next-step"),
            @FindBy(css = "button.btn.btnNext.customer-hide")})
    public WebElement checkoutNowButton;

    @Step("Verify Old checkout shopping cart page is displayed")
    public void verifyShoppingCartPageIsDisplayed() throws InterruptedException {
        jsWaitForPageToLoad(10);
        Assert.assertTrue(driver.getCurrentUrl().contains("/tools/checkout"), "The Old Checkout is not loaded or displayed");
    }

    @Step("Get number of products in shopping cart")
    public int getNrOfProductsInCart() {
        step("Nr of products: " + productsList.size());
        return productsList.size();
    }

    @Step("Remove random product from Shopping Cart")
    public void removeRandomProductFromShoppingCart() {
        int random = BasePage.getRandomNumber(removeProductButtonsList.size());
        clickElement(removeProductButtonsList.get(random), "Remove product button");
    }

    @Step("Verify shopping cart is empty")
    public void verifyShoppingCartIsEmpty() {
        step("Check 'emptyTitleForShoppingCart' element is displayed");
        softAssert.assertTrue(isElementDisplayed(emptyTitleForShoppingCart), "Empty shopping cart text is not present");
        step("Check emptyTitleForShoppingCart text is correct");
        softAssert.assertEquals(emptyTitleForShoppingCart.getText(), "Der Warenkorb ist leer.", "Empty shopping cart text is not correct");
        step("Check products list in empty");
        softAssert.assertEquals(productsList.size(), 0,   "Products list from shopping cart is not empty");

        softAssert.assertAll("Shopping cart is not empty");
    }

    @Step("Verify size of shopping Cart list")
    public void verifySizeOfShoppingCartList(int size) throws InterruptedException {
        jsWaitForPageToLoad(10);
        Assert.assertEquals(productsList.size(), size, "The product was not added to cart");
    }
}
