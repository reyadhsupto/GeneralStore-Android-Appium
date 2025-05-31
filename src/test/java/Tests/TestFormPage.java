package Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.FormPage;

public class TestFormPage extends baseTest{
	
	public String country = "Bangladesh";
	public String name = "Reyad Hassan";
	public String nextPageText = "Products";
	public FormPage form;
	
	@BeforeMethod
	public void initPageObjects() {
        form = new FormPage(driver , wait);
    }
	
	@Test(priority = 1, description = "TC_001: Verify that user is able to fillup the form", enabled = true)
    public void testFormFillup() {
        form.chooseCountry(country);
        Assert.assertTrue(form.isCountrySelected(country), "Country selection failed");
        form.letsShop();
        Assert.assertTrue(form.checkNameError(), "Error message not displayed if name is not provided");
        form.fillName(name);
        form.checkGender();
        form.letsShop();
        Assert.assertTrue(form.checkNextPage(nextPageText), "User no redirected to products page upon form fillup!");
    }

}
