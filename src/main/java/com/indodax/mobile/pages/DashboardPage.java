package com.indodax.mobile.pages;

import io.appium.java_client.AppiumDriver;

public class DashboardPage extends BasePage {

    public DashboardPage(AppiumDriver driver) {
        super(driver);
    }

    public void waitForPageLoad() {
        waitForElement("dashboard.products");
    }

    public boolean isDashboardDisplayed() {
        return isDisplayed("dashboard.products");
    }

    public boolean isMenuButtonVisible() {
        return isDisplayed("dashboard.menu.button");
    }

    public int getProductCount() {
        return findAll("dashboard.product.title").size();
    }

    public boolean isCartBadgeDisplayed() {
        return isDisplayed("dashboard.cart.icon");
    }

    public void openMenu() {
        click("dashboard.menu.button");
    }

    public void logout() {
        clickAndWait("dashboard.menu.button");
        clickAndWait("menu.logout");
    }
}
