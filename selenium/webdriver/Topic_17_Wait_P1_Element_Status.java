package webdriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_17_Wait_P1_Element_Status {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new FirefoxDriver();	
		
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://www.facebook.com/");
	}
	
	@Test
	public void TC_01_Visible() {
		// Visible ~ UI: CÓ - DOM: CÓ
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
		
		Assert.assertTrue(driver.findElement(By.cssSelector("input#email")).isDisplayed());
	}

	@Test
	public void TC_02_Invisible() {
		// Open "Sign Up" form
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		
		// Invisible ~ UI: KHÔNG - DOM: có
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
		Assert.assertFalse(driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']")).isDisplayed());
		
		// ----------------------------------------------------------------------------------------------------
		
		// Close "Sign Up" form
		driver.findElement(By.xpath("//div[contains(text(), 'easy.')]/parent::div/preceding-sibling::img")).click();
		sleepInSecond(3);
		
		// Invisible ~ UI: KHÔNG - DOM: không
		// Chạy mất 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
		
		// Chạy mất 15s -> Fail
		// Thứ tự: findElement -> isDisplayed -> assert
		Assert.assertFalse(driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']")).isDisplayed());
	}
	
	@Test
	public void TC_03_Presence() {
		// Presence ~ DOM: CÓ - UI: có
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#email")));
		
		// ----------------------------------------------------------------------------------------------------
		
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();	
		
		// Presence ~ DOM: CÓ - UI: không
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}
	
	@Test
	public void TC_04_Staleness() {
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		sleepInSecond(3);
		
		// confirmationEmailTextbox có trong DOM
		WebElement confirmationEmailTextbox = driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']"));
		
		driver.findElement(By.xpath("//div[contains(text(), 'easy.')]/parent::div/preceding-sibling::img")).click();
		
		// Staleness ~ DOM: KHÔNG - UI: KHÔNG
		explicitWait.until(ExpectedConditions.stalenessOf(confirmationEmailTextbox));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}