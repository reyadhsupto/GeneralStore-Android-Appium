package Pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;


public class ProductPage extends BasePage{
	//Selectors
	private final By productName = AppiumBy.id("com.androidsample.generalstore:id/productName");
	private final By productsContainer = AppiumBy.id("com.androidsample.generalstore:id/rvProductList");
	private final By productContainer = AppiumBy.className("android.widget.LinearLayout");
	private final By addToCartBtn = AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart");
	
	private final By cartProductName = AppiumBy.id("com.androidsample.generalstore:id/productName");
	private final By cartProdcutPrice = AppiumBy.id("com.androidsample.generalstore:id/productPrice");
	private final By carttotalPrice = AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl");
	
	private final By termsConditions = AppiumBy.id("com.androidsample.generalstore:id/termsButton");
	private final By termsModal = AppiumBy.id("com.androidsample.generalstore:id/alertTitle");
	private final By termsConditonsClosewBtn = AppiumBy.xpath("//android.widget.Button[@text='CLOSE']");
	private final By cartPageTile = AppiumBy.id("com.androidsample.generalstore:id/toolbar_title");
	
	
	public ProductPage(AppiumDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actionChain = new Actions(driver);
	}
	
	
	public boolean findProduct(String name) {
		//first scroll to product by name
		ScrollToText(name);
		
		//iterate through the visible products
	    List<WebElement> productList = GetElements(productName);
	    
	    for (WebElement productElement : productList) {
	        if (productElement.getText().trim().equalsIgnoreCase(name)) return true;
	    }
	    return false;
	}
	
	public Map<String, String> addToCart(String name) {
		//first scroll to product by name
		ScrollToText(name);

	    WebElement productListContainer = GetElement(productsContainer);

	    List<WebElement> productContainers = productListContainer.findElements(productContainer);
	    
	    Map<String, String> productInfo = new HashMap<>();
	    for (WebElement product : productContainers) {
	        try {
	        	String productName = product.findElement(AppiumBy.xpath(".//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productName']")).getText().trim();
	        	if (productName.equalsIgnoreCase(name)) {
	        		String productPrice = product.findElement(AppiumBy.xpath(".//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productPrice']")).getText().trim().replaceAll("[^\\d.]", "");
		            productInfo.put(productName, productPrice);
		            
		            //adding to cart
		            WebElement addToCartButton = product.findElement(AppiumBy.xpath(".//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productAddCart']"));
	                addToCartButton.click();
	        	}
	        } catch (Exception e) {
	            System.out.println("Skipping a product due to missing info.");
	        }
	    }

	    return productInfo;
	}
	
	
	public void clickAddtoCartBtn() {
		ClickElement(addToCartBtn);
	}
	
	
	public boolean validateCartProduct(String name, String price) {
		String actualName = wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductName)).getText().trim(); //was getting stale reference error
	    
	    if (!actualName.equalsIgnoreCase(name)) {
	        System.out.println("Product name mismatch: expected '" + name + "', but found '" + actualName + "'"); 
	        return false;
	    }
	    
	    String actualPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(cartProdcutPrice)).getText().trim(); //was getting stale reference error
	    if (!actualPrice.contains(price)) {
	        System.out.println("Price mismatch for '" + actualName + "': expected '" + price + "', but found '" + actualPrice + "'");
	        return false;
	    }
	    return true;
	}
	
	
	public boolean validateCartTotalPrice(String expectedTotalPrice) {
		String actualTotal = driver.findElement(carttotalPrice).getText().trim().replaceAll("[^\\d.]", "");

	    try {
	        double actual = Double.parseDouble(actualTotal);
	        double expected = Double.parseDouble(expectedTotalPrice);

	        // Considering minor float total reference
	        if (Math.abs(actual - expected) > 0.01) {
	            System.out.println("Cart total mismatch: expected " + expected + ", found " + actual);
	            return false;
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Error parsing price values.");
	        return false;
	    }

	    return true;
	}
	
	public void pressTerms() {
		LongPress(termsConditions, 3);
	}
	
	public boolean checkTermsModal(String text) {
		return (GetElement(termsModal).getText().trim()).equals(text);
	}
	
	public void closeTermsModal() {
		ClickElement(termsConditonsClosewBtn);
	}
	
	public boolean checkTermsClosing() {
		return (GetElement(cartPageTile).getText().trim()).equals("Cart");
	}

}
