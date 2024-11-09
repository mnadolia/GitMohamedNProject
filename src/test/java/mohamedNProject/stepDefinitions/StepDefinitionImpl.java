package mohamedNProject.stepDefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mohamedNProject.TestComponents.BaseTest;
import mohamedNProject.pageobjects.CartPage;
import mohamedNProject.pageobjects.CheckoutPage;
import mohamedNProject.pageobjects.ConfirmationPage;
import mohamedNProject.pageobjects.LandingPage;
import mohamedNProject.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest{

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
		//code
	}


	

	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username,password);
	}
	
	
	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName)
	{
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);

		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".popup-class"))); // Replace with the actual locator

		
		WebElement checkoutButton = driver.findElement(By.cssSelector(".totalRow button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutButton);

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		//By cssSelector
		//driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)"));
		
		//By xpath - Exception in thread "main" org.openqa.selenium.ElementClickInterceptedException: element click intercepted: Element is not clickable at point (1011, 558)
		//driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
		
		//solved
		WebElement element1 = driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
		
		//Solved
		//driver.findElement(By.cssSelector(".action__submit")).click();		
		WebElement element2 = driver.findElement(By.cssSelector(".action__submit"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);
	
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
		
	}
	
	
    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_confirmationPage(String string)
    {
    	String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
    }
    
    @Then("^\"([^\"]*)\" message is displayed$")
    public void something_message_is_displayed(String strArg1) throws Throwable {
   
    	Assert.assertEquals(strArg1, landingPage.getErrorMessage());
    	driver.close();
    }

}
