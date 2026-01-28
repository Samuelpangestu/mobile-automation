package com.indodax.mobile.pages;

import com.indodax.mobile.utils.LocatorReader;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends BasePage {

    public ProductPage(AppiumDriver driver) {
        super(driver);
    }

    public void clickFirstProduct() {
        List<WebElement> products = findAll("dashboard.product.title");
        if (!products.isEmpty()) {
            products.get(0).click();
            waitForAnimation();
        }
    }

    public void clickAddToCart() {
        waitForElementClickable("product.add.to.cart.button");
        clickAndWait("product.add.to.cart.button");
    }

    public void clickSortButton() {
        clickAndWait("dashboard.sort.button");
    }

    public void selectSortOption(String option) {
        String locatorKey = switch (option.toLowerCase()) {
            case "name - ascending" -> "sort.option.name.asc";
            case "name - descending" -> "sort.option.name.desc";
            case "price - ascending" -> "sort.option.price.asc";
            case "price - descending" -> "sort.option.price.desc";
            default -> throw new RuntimeException("Unknown sort option: " + option);
        };
        clickAndWait(locatorKey);
    }

    public String getCartBadgeCount() {
        return isPresent("cart.badge.counter") ? getText("cart.badge.counter") : "0";
    }

    public boolean isProductDetailsDisplayed() {
        return isDisplayed("product.detail.name");
    }

    public boolean isProductNameVisible() {
        return isDisplayed("product.detail.name");
    }

    public boolean isProductPriceVisible() {
        return isDisplayed("product.detail.price");
    }

    public void openCart() {
        clickAndWait("dashboard.cart.icon");
    }

    public void removeProductFromCart() {
        waitForElementClickable("cart.remove.button");
        clickAndWait("cart.remove.button");
    }

    public boolean isCartEmpty() {
        return isDisplayed("cart.empty.message");
    }

    public boolean areProductsSortedByPriceAscending() {
        List<WebElement> prices = findAll("dashboard.product.price");
        if (prices.size() < 2) return true;

        String price1 = prices.get(0).getText().replace("$", "").trim();
        String price2 = prices.get(1).getText().replace("$", "").trim();

        double p1 = Double.parseDouble(price1);
        double p2 = Double.parseDouble(price2);

        return p1 <= p2;
    }
}
