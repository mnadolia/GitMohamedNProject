package mohamedNProject.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import mohamedNProject.TestComponents.BaseTest;
import mohamedNProject.TestComponents.Retry;
import mohamedNProject.pageobjects.CartPage;
import mohamedNProject.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest 
{
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException 
	{
		landingPage.loginApplication("nadoliam@gmail.com", "Zxcv%4321");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("nadoliam@gmail.com", "Zxcv%4321");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	}
}