package com.indodax.mobile.pages;

import io.appium.java_client.AppiumDriver;

public class LoginPage extends BasePage {

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public void navigateToLogin() {
        waitForElement("login.menu.button");
        clickAndWait("login.menu.button");
        clickAndWait("login.menu.item");
    }

    public void waitForPageLoad() {
        waitForElement("login.username");
    }

    public void enterUsername(String username) {
        type("login.username", username);
    }

    public void enterPassword(String password) {
        type("login.password", password);
    }

    public void clickLoginButton() {
        click("login.button");
    }

    public void login(String username, String password) {
        waitForPageLoad();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed("login.error");
    }
}
