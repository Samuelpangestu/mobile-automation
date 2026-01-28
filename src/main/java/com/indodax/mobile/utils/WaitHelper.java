package com.indodax.mobile.utils;

public class WaitHelper {
    private static final long ANIMATION_WAIT_MS;
    private static final long NAVIGATION_WAIT_MS;
    private static final long SHORT_WAIT_MS = 500;

    static {
        ANIMATION_WAIT_MS = ConfigReader.getAnimationTimeout();
        NAVIGATION_WAIT_MS = ConfigReader.getNavigationTimeout();
    }

    public static void waitForAnimation() {
        sleep(ANIMATION_WAIT_MS);
    }

    public static void waitForNavigation() {
        sleep(NAVIGATION_WAIT_MS);
    }

    public static void waitShort() {
        sleep(SHORT_WAIT_MS);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
