package factory;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import resources.AppUtils;

public class CapabilitiesManager {
	
	public static Capabilities getAndroidCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("EmulatorReyad");
        options.setAutomationName("UiAutomator2");

        options.setApp(AppUtils.getAppPath());
        return options;
    }

}
