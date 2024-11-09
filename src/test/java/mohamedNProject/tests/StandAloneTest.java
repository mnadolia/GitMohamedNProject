package mohamedNProject.tests;

import java.time.Duration;
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

import io.github.bonigarcia.wdm.WebDriverManager;
import mohamedNProject.pageobjects.LandingPage;

public class StandAloneTest 
{	
	public static void main(String[] args) 
	{
	
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("nadoliam@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Zxcv%4321");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod =	products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));		
		Boolean match = 	cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
		
	}
	
	
	
	

	
	
	/*			--Working code--
	public static void main(String[] args) throws InterruptedException 
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		String productName = "ZARA COAT 3";
		
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
		//
		
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
		
	}
	*/
}