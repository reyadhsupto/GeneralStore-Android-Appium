package resources;
import java.io.File;

public class AppUtils {
	
	public static String getAppPath() {
//        String appRelativePath = "src/main/java/resources/ApiDemos-debug.apk";
		String appRelativePath = "src/main/java/resources/General-Store.apk";
        File appFile = new File(System.getProperty("user.dir"), appRelativePath);
        return appFile.getAbsolutePath();
    }

}

/**
 * for appium Inspector
{
  "app": "/Users/reyad/eclipse-workspace/GeneralStore/src/main/java/resources/General-Store.apk",
  "deviceName": "EmulatorReyad",
  "platformName": "android",
  "automationName": "UiAutomator2"
} 
 * */

/**
appium --address 127.0.0.1 --port 4723
**/