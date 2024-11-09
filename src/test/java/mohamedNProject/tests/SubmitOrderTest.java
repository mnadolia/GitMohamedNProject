package mohamedNProject.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import mohamedNProject.TestComponents.BaseTest;
import mohamedNProject.pageobjects.CartPage;
import mohamedNProject.pageobjects.CheckoutPage;
import mohamedNProject.pageobjects.ConfirmationPage;
import mohamedNProject.pageobjects.LandingPage;
import mohamedNProject.pageobjects.OrderPage;
import mohamedNProject.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest
{
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		
		//"ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("nadoliam@gmail.com", "Zxcv%4321");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	//Extent Reports - 
		
	@DataProvider
	public Object[][] getData() throws IOException
	{
	
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//PurchaseOrder.json");
		return new Object[][]  {{data.get(0)}, {data.get(1) } };
		
	}	
	
//	 @DataProvider
//	 public Object[][] getData()
//	 {
//	    return new Object[][]  {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
//	    
//	 }
//	HashMap<String,String> map = new HashMap<String,String>();
//	map.put("email", "anshika@gmail.com");
//	map.put("password", "Iamking@000");
//	map.put("product", "ZARA COAT 3");
//	
//	HashMap<String,String> map1 = new HashMap<String,String>();
//	map1.put("email", "shetty@gmail.com");
//	map1.put("password", "Iamking@000");
//	map1.put("product", "ADIDAS ORIGINAL");
		
		
		/*		All >> Working Code --
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		String productName = "ZARA COAT 3";
		
		LandingPage landingpage = new LandingPage(driver);
		landingpage.goTo();
		landingpage.loginApplication("nadoliam@gmail.com", "Zxcv%4321");
		
		
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("nadoliam@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Zxcv%4321");
		driver.findElement(By.id("login")).click();

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		

		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		//ng-animating
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("ng-animating")));
	
		//wait2.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("ng-animating"))));
		//ng-trigger-fadeIn ng-star-inserted ng-animating - By mistake I found className: 
		//Do mistake of invisibilityOf method instead of ElementLocated.
	
		//wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("ng-animating"))));
		
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase
				(productName));
		
		Assert.assertTrue(match);
		
		//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".totalRow button")));
		//element.click();
		
		//
		//WebElement element = driver.findElement(By.cssSelector(".totalRow button"));
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		//element.click();
		//
		
		//Reason for not-working: Overlay
		
		//3. Click using JavaScript:
		//	If the element is still not clickable due to an overlay or some other reason, you can force the click using JavaScript.
		
		WebElement element = driver.findElement(By.cssSelector(".totalRow button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		//Not-working
		//driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[3]")).click();
		
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
		
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		//Thread.sleep(2000);
		
		driver.close();
		*/
}