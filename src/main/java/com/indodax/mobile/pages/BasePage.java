package com.indodax.mobile.pages;

import com.indodax.mobile.utils.ConfigReader;
import com.indodax.mobile.utils.LocatorReader;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Base Page - Contains all common methods for Page Objects
 * DRY principle: Don't Repeat Yourself
 */
public abstract class BasePage {
    protected final AppiumDriver driver;
    protected final WebDriverWait wait;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, ConfigReader.getExplicitTimeout());
    }

    // ===== ELEMENT FINDERS =====
    protected WebElement find(String locatorKey) {
        return driver.findElement(LocatorReader.getLocator(locatorKey));
    }

    protected List<WebElement> findAll(String locatorKey) {
        return driver.findElements(LocatorReader.getLocator(locatorKey));
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    // ===== WAIT METHODS =====
    protected WebElement waitForElement(String locatorKey) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                LocatorReader.getLocator(locatorKey)
        ));
    }

    protected WebElement waitForElementVisible(String locatorKey) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                LocatorReader.getLocator(locatorKey)
        ));
    }

    protected WebElement waitForElementClickable(String locatorKey) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                LocatorReader.getLocator(locatorKey)
        ));
    }

    // ===== ACTIONS =====
    protected void click(String locatorKey) {
        find(locatorKey).click();
    }

    protected void clickAndWait(String locatorKey) {
        click(locatorKey);
        sleep(ConfigReader.getAnimationTimeout());
    }

    protected void type(String locatorKey, String text) {
        WebElement element = find(locatorKey);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(String locatorKey) {
        return find(locatorKey).getText();
    }

    // ===== CHECKS =====
    protected boolean isDisplayed(String locatorKey) {
        try {
            return find(locatorKey).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isPresent(String locatorKey) {
        try {
            find(locatorKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===== WAIT HELPERS =====
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void waitForAnimation() {
        sleep(ConfigReader.getAnimationTimeout());
    }

    protected void waitForNavigation() {
        sleep(ConfigReader.getNavigationTimeout());
    }
}
