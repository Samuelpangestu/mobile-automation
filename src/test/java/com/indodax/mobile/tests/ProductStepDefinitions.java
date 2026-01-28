package com.indodax.mobile.tests;

import com.indodax.mobile.pages.ProductPage;
import com.indodax.mobile.utils.DriverFactory;
import com.indodax.mobile.utils.WaitHelper;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ProductStepDefinitions {
    private AppiumDriver driver;
    private ProductPage productPage;

    public ProductStepDefinitions() {
        this.driver = DriverFactory.getDriver();
        this.productPage = new ProductPage(driver);
    }

    @When("I click on the first product")
    public void iClickOnTheFirstProduct() {
        productPage.clickFirstProduct();
    }

    @And("I click add to cart button")
    public void iClickAddToCartButton() {
        productPage.clickAddToCart();
    }

    @Then("the product should be added to cart")
    public void theProductShouldBeAddedToCart() {
        // Wait for cart to update
        WaitHelper.waitShort();
    }

    @And("cart badge should show {string} item")
    public void cartBadgeShouldShowItem(String expectedCount) {
        String actualCount = productPage.getCartBadgeCount();
        Assertions.assertEquals(expectedCount, actualCount, "Cart count mismatch");
    }

    @When("I click on the sort button")
    public void iClickOnTheSortButton() {
        productPage.clickSortButton();
    }

    @And("I select sort by {string}")
    public void iSelectSortBy(String sortOption) {
        productPage.selectSortOption(sortOption);
    }

    @Then("products should be sorted by price ascending")
    public void productsShouldBeSortedByPriceAscending() {
        boolean isSorted = productPage.areProductsSortedByPriceAscending();
        Assertions.assertTrue(isSorted, "Products not sorted correctly");
    }

    @Then("product details should be displayed")
    public void productDetailsShouldBeDisplayed() {
        boolean isDisplayed = productPage.isProductDetailsDisplayed();
        Assertions.assertTrue(isDisplayed, "Product details not displayed");
    }

    @And("product name should be visible")
    public void productNameShouldBeVisible() {
        boolean isVisible = productPage.isProductNameVisible();
        Assertions.assertTrue(isVisible, "Product name not visible");
    }

    @And("product price should be visible")
    public void productPriceShouldBeVisible() {
        boolean isVisible = productPage.isProductPriceVisible();
        Assertions.assertTrue(isVisible, "Product price not visible");
    }

    @And("I open the cart")
    public void iOpenTheCart() {
        productPage.openCart();
    }

    @And("I remove the product from cart")
    public void iRemoveTheProductFromCart() {
        productPage.removeProductFromCart();
    }

    @Then("cart should be empty")
    public void cartShouldBeEmpty() {
        boolean isEmpty = productPage.isCartEmpty();
        Assertions.assertTrue(isEmpty, "Cart is not empty");
    }
}
