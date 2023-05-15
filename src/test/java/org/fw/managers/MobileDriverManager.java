package org.fw.managers;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.EnhancedIOSDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.fw.enums.Platform;
import org.fw.utils.Constants;
import org.fw.utils.PropertyUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {

    private static final String PLATFORM_VERSION = PropertyUtils.getProperty(Constants.PLATFORM_VERSION);
    private static final String PLATFORM_NAME = PropertyUtils.getProperty(Constants.PLATFORM_NAME);
    private static final String DEVICE_NAME = PropertyUtils.getProperty(Constants.DEVICE_NAME);
    private static final String APP_NAME = PropertyUtils.getProperty(Constants.APP_NAME);
    private static final String AUTOMATION_NAME = PropertyUtils.getProperty(Constants.AUTOMATION_NAME);
    private static final String APPIUM_URL = PropertyUtils.getProperty(Constants.APPIUM_URL);

    private static final ThreadLocal<AppiumDriver<WebElement>> mobileDrivers = new ThreadLocal<>();

    public static synchronized AppiumDriver<WebElement> getMobileDriver() {
        return mobileDrivers.get();
    }

    public static synchronized void setMobileDrivers(AppiumDriver<WebElement> driver) {
        mobileDrivers.set(driver);
    }

    public static synchronized Boolean doesDriverExist() {
        return getMobileDriver() != null;
    }

    public static synchronized Boolean isDriverSessionActive() {
        return getDriverSessionId() != null;
    }

    private static synchronized SessionId getDriverSessionId() {
        SessionId sessionId = null;

        if (doesDriverExist()) {
            sessionId = MobileDriverManager.getMobileDriver().getSessionId();
        }

        return sessionId;
    }

    public static IOSDriver<WebElement> createIOSDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=====> Creating new iOS driver ===");

        IOSDriver<WebElement> driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("ios" + File.separator + APP_NAME));

        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, true);

        try {
            driver = new IOSDriver<>(new URL(APPIUM_URL), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating iOS driver", exception);
        }

        return driver;
    }

    public static AndroidDriver<WebElement> createAndroidDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=====> Creating new Android driver ===");

        AndroidDriver<WebElement> driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("android" + File.separator + APP_NAME));

        try {
            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating Android driver", exception);
        }

        return driver;
    }

    public static EnhancedAndroidDriver<WebElement> createAzureAndroidDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=====> Creating new Azure Android driver");

        EnhancedAndroidDriver<WebElement> driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("android" + File.separator + APP_NAME));

        try {
            driver = Factory.createAndroidDriver(new URL(APPIUM_URL), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating Azure Android driver", exception);
        }

        return driver;
    }

    public static EnhancedIOSDriver<WebElement> createAzureIOSDriver() {
        LoggingManager.logInfo(MobileDriverManager.class, "=====> Creating new Azure iOS driver ===");

        EnhancedIOSDriver<WebElement> driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability(MobileCapabilityType.APP, getAppAbsolutePath("ios" + File.separator + APP_NAME));

        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 500000);

        try {
            driver = Factory.createIOSDriver(new URL(APPIUM_URL), capabilities);
        } catch (MalformedURLException exception) {
            LoggingManager.logError(MobileDriverManager.class, "Error when creating Azure iOS driver", exception);
        }

        return driver;
    }

    public static void createMobileDriver() {
        if (System.getenv("APP_CENTER_TEST") != null) {
            if (PropertyUtils.isPlatform(Platform.ANDROID)) {
                EnhancedAndroidDriver<WebElement> driver = createAzureAndroidDriver();
                setMobileDrivers(driver);
            } else if (PropertyUtils.isPlatform(Platform.IOS)) {
                EnhancedIOSDriver<WebElement> driver = createAzureIOSDriver();
                setMobileDrivers(driver);
            }
        } else {
            if (PropertyUtils.isPlatform(Platform.IOS)) {
                IOSDriver<WebElement> driver = createIOSDriver();
                setMobileDrivers(driver);
            } else if (PropertyUtils.isPlatform(Platform.ANDROID)) {
                AndroidDriver<WebElement> driver = createAndroidDriver();
                setMobileDrivers(driver);
            }
        }
    }

    private static String getAppAbsolutePath(String appName) {
        return PropertyUtils.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "apps" + File.separator + appName;
    }

    public static void tearDownDriver() {
        AppiumDriver<WebElement> driver = getMobileDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
