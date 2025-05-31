package Tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.ProductPage;
import Pages.FormPage;

public class TestProductPage extends baseTest{
	public ProductPage productP;
	public FormPage form;
	private final String productName = "Air Jordan 9 Retro";
	private Map<String, String> productInfo;
	
	@BeforeMethod
	public void initPageObjects() {
		productP = new ProductPage(driver, wait);
		form = new FormPage(driver, wait);
	}
	
	@Test(priority = 1, description = "TC_002: Verify that product page accurately displays specified products", enabled = false)
	public void TestFindProduct() {
		//formpage actions
		form.chooseCountry("Bangladesh");
        form.fillName("ReyadHassan");
        form.checkGender();
        form.letsShop();
        
        //productpage actions
		Assert.assertTrue(productP.findProduct(productName), "Product not found!");
	}
	
	@Test(priority = 2, description = "TC_003: Verify that add to cart functionality is working properly", enabled = false)
	public void TestCartProducts() {
		//formpage actions
		form.chooseCountry("Bangladesh");
        form.fillName("ReyadHassan");
        form.checkGender();
        form.letsShop();
        
        //productpage actions
        productInfo = productP.addToCart(productName);
        productP.clickAddtoCartBtn();
        
        String cartProductName = productInfo.keySet().iterator().next();
        String cartProductPrice = productInfo.values().iterator().next();
        Assert.assertTrue(productP.validateCartProduct(cartProductName, cartProductPrice), "Cart product name and price doesn't match with added product name and price");
	}
	
	private String priceConverter(Map<String, String> productMap) {
		double total = 0.0;
		for(String price: productMap.values()) {
			try {
				total += Double.parseDouble(price.replaceAll("[^\\d.]", ""));
			} catch (NumberFormatException e) {
				System.out.println("Invalid Price Format: " + price);
			}
		}
		System.out.println("total price in double format: " + total);
		return String.format("%.2f", total);
	}
	
	@Test(priority = 3, description = "TC_004: Verify that Total checkout price is accurately displayed in Cart page", enabled = true)
	public void TestCartTotalPrice() {
		//formpage actions
		form.chooseCountry("Bangladesh");
        form.fillName("ReyadHassan");
        form.checkGender();
        form.letsShop();
        
        //productpage actions
        productInfo = productP.addToCart(productName);
        productP.clickAddtoCartBtn();
        
        Assert.assertTrue(productP.validateCartTotalPrice(priceConverter(productInfo)), "App displays inaccurate total checkout price in cart page!");
	}
	

}
