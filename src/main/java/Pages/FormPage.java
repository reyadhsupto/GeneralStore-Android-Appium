package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class FormPage extends BasePage{
	
	private final By yourName = AppiumBy.id("com.androidsample.generalstore:id/nameField");
	private final By radioMale = AppiumBy.id("com.androidsample.generalstore:id/radioMale");
	private final By letShop = AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop");
	private final By dropdown = AppiumBy.id("android:id/text1");
	private String country = "";
	
	private final By nextPage = AppiumBy.id("com.androidsample.generalstore:id/toolbar_title");
	public final By nameError = AppiumBy.xpath("//android.widget.Toast[1]");
	
	private By getDynamicCountryLocator(String countryName) {
	    return By.xpath(String.format("//android.widget.TextView[@text='%s']", countryName));
	}
	
	
	public FormPage(AppiumDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actionChain = new Actions(driver);
    }
	
	
	public void chooseCountry(String countryName) {
		ClickElement(dropdown);
		country = countryName;
		ScrollToText(country);
		ClickElement(getDynamicCountryLocator(countryName));
		
	}
	
	public void fillName(String name) {
		SendKeys(yourName, name);
	}
	
	public void checkGender() {
		ClickElement(radioMale);
	}
	
	public void letsShop() {
		ClickElement(letShop);
	}
	
//	public boolean checkNextPage(String expectedText) {
//		return GetElement(nextPage).getText().trim().equalsIgnoreCase(expectedText);
//	}
	
	public boolean checkNextPage(String expectedText) {
	    try {
	        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(nextPage));
	        return titleElement.getText().trim().equalsIgnoreCase(expectedText);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	public boolean checkNameError() {
		return GetElement(nameError).getText().trim().contains("Please enter your name");
	}
	
	public boolean isCountrySelected(String name) {
		return GetElement(dropdown).getText().trim().equals(name);
	}
}
