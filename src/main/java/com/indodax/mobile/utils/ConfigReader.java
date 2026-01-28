package com.indodax.mobile.utils;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE = "config.properties";

    static {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + e.getMessage());
        }
    }

    // ===== GENERIC GETTERS =====
    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static Duration getDuration(String key) {
        return Duration.ofSeconds(getInt(key));
    }

    // ===== APPIUM SERVER =====
    public static String getAppiumServerUrl() {
        return get("appium.server.url");
    }

    public static Duration getCommandTimeout() {
        return getDuration("appium.command.timeout");
    }

    // ===== TIMEOUTS =====
    public static Duration getImplicitTimeout() {
        return getDuration("timeout.implicit");
    }

    public static Duration getExplicitTimeout() {
        return getDuration("timeout.explicit");
    }

    public static Duration getPageLoadTimeout() {
        return getDuration("timeout.page.load");
    }

    public static Duration getElementTimeout() {
        return getDuration("timeout.element");
    }

    public static long getAnimationTimeout() {
        return getInt("timeout.animation") * 1000L;
    }

    public static long getNavigationTimeout() {
        return getInt("timeout.navigation") * 1000L;
    }

    // ===== ANDROID CONFIG =====
    public static String getAndroidAutomationName() {
        return get("android.automation.name");
    }

    public static String getAndroidDeviceName() {
        return get("android.device.name");
    }

    public static String getAndroidAppPackage() {
        return get("android.app.package");
    }

    public static String getAndroidAppActivity() {
        return get("android.app.activity");
    }

    public static String getAndroidAppPath() {
        return get("android.app.path");
    }

    public static boolean getAndroidAutoGrantPermissions() {
        return getBoolean("android.auto.grant.permissions");
    }

    public static boolean getAndroidNoReset() {
        return getBoolean("android.no.reset");
    }

    // ===== iOS CONFIG =====
    public static String getIosAutomationName() {
        return get("ios.automation.name");
    }

    public static String getIosDeviceName() {
        return get("ios.device.name");
    }

    public static String getIosPlatformVersion() {
        return get("ios.platform.version");
    }

    public static String getIosAppPath() {
        return get("ios.app.path");
    }

    public static boolean getIosAutoAcceptAlerts() {
        return getBoolean("ios.auto.accept.alerts");
    }

    public static boolean getIosNoReset() {
        return getBoolean("ios.no.reset");
    }

    // ===== TEST CREDENTIALS =====
    public static String getValidUsername() {
        return get("test.user.valid.username");
    }

    public static String getValidPassword() {
        return get("test.user.valid.password");
    }

    public static String getInvalidUsername() {
        return get("test.user.invalid.username");
    }

    public static String getInvalidPassword() {
        return get("test.user.invalid.password");
    }

    // ===== RETRY & STABILITY =====
    public static int getMaxRetryAttempts() {
        return getInt("retry.max.attempts");
    }

    public static long getRetryDelay() {
        return getInt("retry.delay");
    }

    public static boolean isScreenshotOnFailure() {
        return getBoolean("screenshot.on.failure");
    }

    // ===== LOGGING =====
    public static String getLogLevel() {
        return get("log.level");
    }

    public static boolean isAppiumLogEnabled() {
        return getBoolean("log.appium");
    }

    public static boolean isSeleniumLogEnabled() {
        return getBoolean("log.selenium");
    }
}
