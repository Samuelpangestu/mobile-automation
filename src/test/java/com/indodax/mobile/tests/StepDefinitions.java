package com.indodax.mobile.tests;

import com.indodax.mobile.pages.DashboardPage;
import com.indodax.mobile.pages.LoginPage;
import com.indodax.mobile.utils.ConfigReader;
import com.indodax.mobile.utils.DriverFactory;
import com.indodax.mobile.utils.WaitHelper;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class StepDefinitions {
    private AppiumDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private Scenario scenario;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        String platform = System.getProperty("platform", "android");
        DriverFactory.initializeDriver(platform);
        driver = DriverFactory.getDriver();

        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            // Take screenshot on failure
            if (scenario.isFailed() && ConfigReader.isScreenshotOnFailure()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            }
            DriverFactory.quitDriver();
        }
    }

    @Given("the SauceDemo mobile app is launched")
    public void theSauceDemoMobileAppIsLaunched() {
        dashboardPage.waitForPageLoad();
        loginPage.navigateToLogin();
    }

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }

    @When("I logout from the dashboard")
    public void iLogoutFromTheDashboard() {
        dashboardPage.logout();
    }

    @When("I open the menu")
    public void iOpenTheMenu() {
        dashboardPage.openMenu();
        WaitHelper.waitForAnimation();
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        WaitHelper.waitForNavigation();
        dashboardPage.waitForPageLoad();
    }

    @Then("the dashboard should be displayed")
    public void theDashboardShouldBeDisplayed() {
        boolean isDisplayed = dashboardPage.isDashboardDisplayed();
        Assertions.assertTrue(isDisplayed, "Dashboard not displayed");
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        WaitHelper.waitForAnimation();
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assertions.assertTrue(isErrorDisplayed, "Error message not displayed");
    }

    @Then("I should remain on the login page")
    public void iShouldRemainOnTheLoginPage() {
        WaitHelper.waitForAnimation();
        boolean isDashboardDisplayed = dashboardPage.isDashboardDisplayed();
        Assertions.assertFalse(isDashboardDisplayed, "Should remain on login page");
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        WaitHelper.waitForNavigation();
        loginPage.waitForPageLoad();
    }

    @Then("the menu button should be visible")
    public void theMenuButtonShouldBeVisible() {
        boolean isVisible = dashboardPage.isMenuButtonVisible();
        Assertions.assertTrue(isVisible, "Menu button not visible");
    }

    @Then("products should be displayed on the dashboard")
    public void productsShouldBeDisplayedOnTheDashboard() {
        int productCount = dashboardPage.getProductCount();
        Assertions.assertTrue(productCount > 0, "No products displayed");
    }

    @Then("the menu should be displayed")
    public void theMenuShouldBeDisplayed() {
        WaitHelper.waitForAnimation();
    }

    @Then("the cart badge should be displayed")
    public void theCartBadgeShouldBeDisplayed() {
        boolean isDisplayed = dashboardPage.isCartBadgeDisplayed();
        Assertions.assertTrue(isDisplayed, "Cart badge not displayed");
    }
}
