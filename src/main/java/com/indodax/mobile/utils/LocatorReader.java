package com.indodax.mobile.utils;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LocatorReader {
    private static Properties properties;
    private static final String LOCATOR_FILE = "locators-android.properties";

    static {
        properties = new Properties();
        try (InputStream input = LocatorReader.class.getClassLoader().getResourceAsStream(LOCATOR_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + LOCATOR_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load locators: " + e.getMessage());
        }
    }

    /**
     * Get locator by key from properties file
     * Format: type:value (e.g., id:com.example:id/button)
     */
    public static By getLocator(String key) {
        String locator = properties.getProperty(key);

        if (locator == null || locator.trim().isEmpty()) {
            throw new RuntimeException("Locator not found for key: " + key);
        }

        if (locator.contains("NEED_TO_VERIFY")) {
            throw new RuntimeException("Locator needs verification: " + key + " = " + locator);
        }

        String[] parts = locator.split(":", 2);
        if (parts.length != 2) {
            throw new RuntimeException("Invalid locator format for: " + key + " = " + locator);
        }

        String type = parts[0].toLowerCase();
        String value = parts[1];

        switch (type) {
            case "id":
                return AppiumBy.id(value);
            case "xpath":
                return AppiumBy.xpath(value);
            case "accessibility":
                return AppiumBy.accessibilityId(value);
            case "class":
                return AppiumBy.className(value);
            default:
                throw new RuntimeException("Unknown locator type: " + type);
        }
    }

    /**
     * Get raw locator string
     */
    public static String getRawLocator(String key) {
        return properties.getProperty(key);
    }

    /**
     * Check if locator exists
     */
    public static boolean hasLocator(String key) {
        return properties.containsKey(key);
    }
}
