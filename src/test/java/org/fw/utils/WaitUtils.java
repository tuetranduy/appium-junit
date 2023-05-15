package org.fw.utils;


import org.fw.managers.LoggingManager;
import org.fw.managers.MobileDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private static final double WAIT_INTERVAL = 1;
    private static final long WAIT_INTERVAL_MS = (long) (WAIT_INTERVAL * 1000);
    private static final long WAIT_TIME = 20;

    public static WebDriverWait waitDefault() {
        return new WebDriverWait(MobileDriverManager.getMobileDriver(), WAIT_TIME, WAIT_INTERVAL_MS);
    }

    public static void sleep(Class<?> className, int seconds) {
        try {
            seconds = seconds * 1000;
            Thread.sleep(seconds);
        } catch (InterruptedException exception) {
            LoggingManager.logError(className, "Error during perform request", exception);
        }
    }

}