package webdriver;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_P7_Fluent {
	WebDriver driver;
	WebElement element;
	WebDriverWait explicitWait;
	
	FluentWait<WebDriver> fluentDriver;
	long sumTime = 30;
	long pollTime = 1;
	
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_01_Fluent_Wait() {
		driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
		
		fluentDriver = new FluentWait<WebDriver>(driver);
		
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		fluentDriver.withTimeout(Duration.ofSeconds(6))
		.pollingEvery(Duration.ofSeconds(1))
		.ignoring(NoSuchElementException.class)
		.until(new Function<WebDriver, String>() {

			@Override
			public String apply(WebDriver driver) {
				String text = driver.findElement(By.cssSelector("div#finish>h4")).getText();
				System.out.println(text);
				return text;
			}
		});
	}
	
	@Test
	public void TC_02_Fluent_Wait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		fluentDriver = new FluentWait<WebDriver>(driver);
		
		clickToElement(By.cssSelector("div#start>button"));
		
		isElementDisplayed(By.cssSelector("div#finish>h4"));
	}
	
	@Test
	public void TC_03_Fluent_Wait() {
		driver.get("https://automationfc.github.io/fluent-wait/");	
		By countdownTimeBy = By.cssSelector("div#javascript_countdown_time");
		
		waitElementVisible(countdownTimeBy);
		
		fluentDriver = new FluentWait<WebDriver>(driver);
		fluentDriver.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofMillis(100))
		.ignoring(NoSuchElementException.class)
		.until(new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				boolean flag = driver.findElement(countdownTimeBy).getText().endsWith("00");
				return flag;
			}
		});	
	}
	
	public WebElement findElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(sumTime))
				.pollingEvery(Duration.ofSeconds(pollTime))
				.ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);	
			}
		});
		return element;
	}
	
	public void clickToElement(By locator) {
		WebElement element = findElement(locator);
		element.click();
	}
	
	public WebElement waitElementVisible(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(sumTime))
				.pollingEvery(Duration.ofSeconds(pollTime))
				.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public boolean isElementDisplayed(By locator) {
		WebElement element = waitElementVisible(locator);
		FluentWait<WebElement> wait = new FluentWait<WebElement>(element)
				.withTimeout(Duration.ofSeconds(sumTime))
				.pollingEvery(Duration.ofSeconds(pollTime))
				.ignoring(NoSuchElementException.class);
		
		boolean isDisplayed = wait.until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement element) {
				boolean flag = element.isDisplayed();
				return flag;
			}
		});
		return isDisplayed;
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}