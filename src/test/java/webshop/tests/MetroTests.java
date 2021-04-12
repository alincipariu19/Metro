package webshop.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import webshop.base.PageLoader;
import webshop.base.BaseTest;
import webshop.data.Constans;
import webshop.data.DataProviders;
import webshop.pages.ProductDetailsPage;
import webshop.pages.ShoppingCartPage;
import static io.qameta.allure.Allure.step;
import static webshop.base.BasePage.jsWaitForPageToLoad;

public class MetroTests extends BaseTest {

    @Description("Check adding and removing 2 products - first approach")
    @Story("Shopping Cart")
    @Link(name="", url="")
    @Test(groups = {"regression"},
            dataProvider = "countryDataProvider",
            dataProviderClass = DataProviders.class)
    public void addAndRemoveProductsFirstApproach(String country) throws InterruptedException {
        String productCode1 = DataProviders.getRandomProductCode();
        String productCode2 = DataProviders.getRandomProductCode();
        while(productCode2.equalsIgnoreCase(productCode1)) {
            productCode2 = DataProviders.getRandomProductCode();
        }
        PageLoader.loadPDPWithSpecificProduct(country,productCode1);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.verifyProductDetailsPageIsDisplayed();
        productDetailsPage.addProductToShoppingCart();
        productDetailsPage.goToShoppingCartButtonFromConfirmationPopup();
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.verifyShoppingCartPageIsDisplayed();
        shoppingCartPage.verifySizeOfShoppingCartList(1);
        PageLoader.loadPDPWithSpecificProduct(country,productCode2);
        productDetailsPage.verifyProductDetailsPageIsDisplayed();
        productDetailsPage.addProductToShoppingCart();
        productDetailsPage.goToShoppingCartButtonFromConfirmationPopup();
        shoppingCartPage.verifyShoppingCartPageIsDisplayed();
        shoppingCartPage.verifySizeOfShoppingCartList(2);
        int nrOfProductsInCart = shoppingCartPage.getNrOfProductsInCart();
        shoppingCartPage.removeRandomProductFromShoppingCart();
        int nrOfProductsInCartAfterRemove = shoppingCartPage.getNrOfProductsInCart();
        step("Check shopping cart products list size has decreased after removing one product");
        Assert.assertEquals(nrOfProductsInCartAfterRemove, nrOfProductsInCart - 1, "Product was not removed from cart");
        shoppingCartPage.removeRandomProductFromShoppingCart();
        shoppingCartPage.verifyShoppingCartIsEmpty();
        shoppingCartPage.removeRandomProductFromShoppingCart();
        step("Check shopping cart products list size has decreased after removing one product");
        Assert.assertEquals(nrOfProductsInCartAfterRemove, nrOfProductsInCart - 1, "Product was not removed from cart");
    }

    @Description("Check adding and removing 2 products - second approach")
    @Story("Shopping Cart")
    @Link(name="", url="")
    @Test(groups = {"regression"},
            dataProvider = "countryDataProvider",
            dataProviderClass = DataProviders.class)
    public void addAndRemoveProductsSecondApproach(String country) throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();

        PageLoader.loadPDPWithSpecificProduct(country, Constans.product1);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        jsWaitForPageToLoad(10);
        step("Check correct Product Details Page is displayed");
        softAssert.assertTrue(driver.getCurrentUrl().toLowerCase().contains(productDetailsPage.articleNumberFromPDP.getText().replace("Artikelnummer: ", "").toLowerCase()), "URL doesn't contain the article number from PDP");
        productDetailsPage.addProductToShoppingCart();
        productDetailsPage.goToShoppingCartButtonFromConfirmationPopup();
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.verifyShoppingCartPageIsDisplayed();
        step("Verify shopping cart size is equal to 1");
        softAssert.assertEquals(shoppingCartPage.productsList.size(), 1, "The product was not added to cart");
        PageLoader.loadPDPWithSpecificProduct(country,Constans.product2);
        step("Check correct Product Details Page is displayed");
        softAssert.assertTrue(driver.getCurrentUrl().toLowerCase().contains(productDetailsPage.articleNumberFromPDP.getText().replace("Artikelnummer: ", "").toLowerCase()), "URL doesn't contain the article number from PDP");
        productDetailsPage.verifyProductDetailsPageIsDisplayed();
        productDetailsPage.addProductToShoppingCart();
        productDetailsPage.goToShoppingCartButtonFromConfirmationPopup();
        shoppingCartPage.verifyShoppingCartPageIsDisplayed();
        step("Verify shopping cart size is equal to 2");
        softAssert.assertEquals(shoppingCartPage.productsList.size(), 2, "The product was not added to cart");
        int nrOfProductsInCart = shoppingCartPage.getNrOfProductsInCart();
        shoppingCartPage.removeRandomProductFromShoppingCart();
        int nrOfProductsInCartAfterRemove = shoppingCartPage.getNrOfProductsInCart();
        step("Check shopping cart products list size has decreased after removing one product");
        softAssert.assertEquals(nrOfProductsInCartAfterRemove, nrOfProductsInCart - 1, "Product was not removed from cart");
        shoppingCartPage.removeRandomProductFromShoppingCart();
        step("Check 'emptyTitleForShoppingCart' element is displayed");
        softAssert.assertTrue(isElementDisplayed(shoppingCartPage.emptyTitleForShoppingCart), "Empty shopping cart text is not present");
        step("Check emptyTitleForShoppingCart text is correct");
        softAssert.assertEquals(shoppingCartPage.emptyTitleForShoppingCart.getText(), "Der Warenkorb ist leer.", "Empty shopping cart text is not correct");
        step("Check products list in empty");
        softAssert.assertEquals(shoppingCartPage.productsList.size(), 0,   "Products list from shopping cart is not empty");
        nrOfProductsInCart = shoppingCartPage.getNrOfProductsInCart();
        shoppingCartPage.removeRandomProductFromShoppingCart();
        nrOfProductsInCartAfterRemove = shoppingCartPage.getNrOfProductsInCart();
        step("Check shopping cart products list size has decreased after removing one product");
        softAssert.assertEquals(nrOfProductsInCartAfterRemove, nrOfProductsInCart - 1, "Product was not removed from cart");
        softAssert.assertAll();
    }
}
