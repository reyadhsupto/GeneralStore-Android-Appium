package factory;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class DriverFactory {
	private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static void setDriver() throws MalformedURLException, URISyntaxException {
        Capabilities capabilities = CapabilitiesManager.getAndroidCapabilities();
        AppiumDriver localDriver = new AppiumDriver(new URI("http://127.0.0.1:4723").toURL(), capabilities);
        driver.set(localDriver);
    }

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

}
