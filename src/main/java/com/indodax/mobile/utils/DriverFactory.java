package com.indodax.mobile.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static AppiumDriver driver;

    public static AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized");
        }
        return driver;
    }

    public static void initializeDriver(String platform) {
        String platformName = platform != null ? platform.toLowerCase() : "android";

        try {
            if (platformName.equals("android")) {
                driver = createAndroidDriver();
            } else if (platformName.equals("ios")) {
                driver = createIOSDriver();
            } else {
                throw new IllegalArgumentException("Platform must be 'android' or 'ios'");
            }

            driver.manage().timeouts().implicitlyWait(ConfigReader.getImplicitTimeout());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize driver: " + e.getMessage(), e);
        }
    }

    private static AndroidDriver createAndroidDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName(ConfigReader.getAndroidAutomationName());
        options.setDeviceName(ConfigReader.getAndroidDeviceName());

        File appFile = new File(ConfigReader.getAndroidAppPath());
        if (!appFile.exists()) {
            appFile = new File("mobile-automation/" + ConfigReader.getAndroidAppPath());
        }
        if (!appFile.exists()) {
            throw new RuntimeException("APK not found: " + ConfigReader.getAndroidAppPath());
        }

        options.setApp(appFile.getAbsolutePath());
        options.setAppPackage(ConfigReader.getAndroidAppPackage());
        options.setAppActivity(ConfigReader.getAndroidAppActivity());
        options.setAutoGrantPermissions(ConfigReader.getAndroidAutoGrantPermissions());
        options.setNewCommandTimeout(ConfigReader.getCommandTimeout());
        options.setNoReset(ConfigReader.getAndroidNoReset());

        return new AndroidDriver(new URL(ConfigReader.getAppiumServerUrl()), options);
    }

    private static IOSDriver createIOSDriver() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("iOS");
        options.setAutomationName(ConfigReader.getIosAutomationName());
        options.setDeviceName(ConfigReader.getIosDeviceName());
        options.setPlatformVersion(ConfigReader.getIosPlatformVersion());

        File appFile = new File(ConfigReader.getIosAppPath());
        if (!appFile.exists()) {
            throw new RuntimeException("IPA not found: " + ConfigReader.getIosAppPath());
        }

        options.setApp(appFile.getAbsolutePath());
        options.setNewCommandTimeout(ConfigReader.getCommandTimeout());
        options.setAutoAcceptAlerts(ConfigReader.getIosAutoAcceptAlerts());
        options.setNoReset(ConfigReader.getIosNoReset());

        return new IOSDriver(new URL(ConfigReader.getAppiumServerUrl()), options);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
