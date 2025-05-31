package Pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumBy;

import com.google.common.collect.ImmutableMap;

public class BasePage {
//	public AndroidDriver driver;
	public AppiumDriver driver;
    public WebDriverWait wait ;
    public Actions actionChain;
	
	private By getBy(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "classname":
                return By.className(locatorValue);
            case "tagname":
                return By.tagName(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "accessibilityid":
                return AppiumBy.accessibilityId(locatorValue);
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
    }
	
	//Clicking an element
	public void ClickElement(String locatorType, String locatorValue) {
		By byLocator = getBy(locatorType, locatorValue);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(byLocator));
        element.click();
    }
	
	//Overload: ClickElement with By
	public void ClickElement(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}
	
	//Returning an element
	public WebElement GetElement(String locatorType, String locatorValue) {
		By byLocator = getBy(locatorType, locatorValue);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
		return element;	
	}
	
	// Overload: GetElement with By
	public WebElement GetElement(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	//Get multiple elements
	public List<WebElement> GetElements(By locator) {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	//Long Press an element
	public void LongPress(By locator, int seconds) {
		actionChain.clickAndHold(GetElement(locator))
        .pause(Duration.ofSeconds(seconds))
        .release()
        .perform();
	}
	
	//Sendkeys to a element
	public void SendKeys(By locator, String text) {
		ClickElement(locator);
		GetElement(locator).sendKeys(text);
		driver.navigate().back();
//		driver.hideKeyboard();
	}
	
	//AI Generated Gesture Methods
	
	// Tap on element (single quick tap)
    public void TapElement(By locator) {
        WebElement element = GetElement(locator);
        actionChain.moveToElement(element).click().perform();
    }

    // Double tap
    public void DoubleTap(By locator) {
        WebElement element = GetElement(locator);
        actionChain.moveToElement(element).doubleClick().perform();
    }

    // Swipe from one element to another
    public void SwipeElementToElement(By fromLocator, By toLocator) {
        WebElement from = GetElement(fromLocator);
        WebElement to = GetElement(toLocator);
        actionChain
            .clickAndHold(from)
            .pause(Duration.ofMillis(300))
            .moveToElement(to)
            .release()
            .perform();
    }

    // Swipe by coordinates (useful for swipe-to-refresh or carousels)
    public void SwipeByCoordinates(By fromLocator, String direction, int distance) {
    	WebElement from = GetElement(fromLocator);

        int startX = from.getLocation().getX() + from.getSize().getWidth() / 2;
        int startY = from.getLocation().getY() + from.getSize().getHeight() / 2;

        int endX = startX;
        int endY = startY;

        switch (direction.toLowerCase()) {
            case "up":
                endY = startY - distance;
                break;
            case "down":
                endY = startY + distance;
                break;
            case "left":
                endX = startX - distance;
                break;
            case "right":
                endX = startX + distance;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    // Scroll to an element containing specific visible text (Android only)
    public void ScrollToText(String visibleText) {
        String uiScrollScript = "new UiScrollable(new UiSelector().scrollable(true)).scrollTextIntoView(\"" + visibleText + "\")";
        driver.findElement(AppiumBy.androidUIAutomator(uiScrollScript));
    }
    
 // Scroll to an element containing specific visible text (IOS only)
    public void scrollToElementByName(String visibleText) {
        driver.executeScript("mobile: scroll", ImmutableMap.of(
            "direction", "down",
            "name", visibleText
        ));
    }

    // Drag and drop
    public void DragAndDropElement(By fromLocator, By toLocator) {
    	WebElement from = GetElement(fromLocator);
        WebElement to = GetElement(toLocator);

        // Get start coordinates (center of source)
        int startX = from.getLocation().getX() + from.getSize().getWidth() / 2;
        int startY = from.getLocation().getY() + from.getSize().getHeight() / 2;

        // Get end coordinates (center of target)
        int endX = to.getLocation().getX() + to.getSize().getWidth() / 2;
        int endY = to.getLocation().getY() + to.getSize().getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);

        dragNDrop.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), startX, startY));
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(800), Origin.viewport(), endX, endY));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(dragNDrop));
    }
	

}
