package Tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import factory.DriverFactory;

public class baseTest {
	public AppiumDriver driver;
    public WebDriverWait wait ;
    public Actions actionChain;
//	AppiumDriverLocalService service;
	
	@BeforeMethod
	public void ConfigureAppium() throws Exception{
		
		DriverFactory.setDriver();
        driver = DriverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actionChain = new Actions(driver);
		
//		service = new AppiumServiceBuilder()
//			    .withAppiumJS(new File("//Users//reyad//node_modules//appium//build//lib//main.js"))
//			    .withIPAddress("127.0.0.1")
//			    .usingPort(4723)
//			    .build();
//
//			service.start();
		
		
		
//		UiAutomator2Options options = new UiAutomator2Options();
//		options.setPlatformName("Android");
//		options.setDeviceName("EmulatorReyad");
//		options.setApp(AppUtils.getAppPath());
//		
//		driver = new AppiumDriver(new URI("http://127.0.0.1:4723").toURL(), options);
//		driver = new AppiumDriver(service.getUrl(), options);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
//		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//		actionChain = new Actions(driver);
	}
	
	@AfterMethod
	public void teardown() {
		DriverFactory.quitDriver();
//		if (service != null && service.isRunning()) service.stop();
	}

}
