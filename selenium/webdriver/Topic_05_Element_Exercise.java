package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Element_Exercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_isDisplayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Email
		Assert.assertTrue(driver.findElement(By.name("user_email")).isDisplayed());		
		// Age (Under 18)
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='under_18']")).isDisplayed());
		// Education
		Assert.assertTrue(driver.findElement(By.xpath("//textarea[@name='user_edu']")).isDisplayed());
		// Name: User5
		Assert.assertFalse(driver.findElement(By.xpath("//h5[text()='Name: User5']")).isDisplayed());
		
		driver.findElement(By.name("user_email")).sendKeys("element@gmail.com");
		driver.findElement(By.xpath("//input[@value='under_18']")).click();
		driver.findElement(By.xpath("//textarea[@name='user_edu']")).sendKeys("Automation Testing");
	}

	@Test
	public void TC_02_isEnabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Email
		Assert.assertTrue(driver.findElement(By.name("user_email")).isEnabled());
		// Age (Under 18)
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='under_18']")).isEnabled());
		// Education
		Assert.assertTrue(driver.findElement(By.xpath("//textarea[@name='user_edu']")).isEnabled());
		// Job Role 01
		Assert.assertTrue(driver.findElement(By.id("job1")).isEnabled());
		// Job Role 02
		Assert.assertTrue(driver.findElement(By.name("user_job2")).isEnabled());
		// Interests (Development)
		Assert.assertTrue(driver.findElement(By.xpath("//label[@for='development']")).isEnabled());
		// Slider 01
		Assert.assertTrue(driver.findElement(By.id("slider-1")).isEnabled());
		
		// Password
		Assert.assertFalse(driver.findElement(By.id("disable_password")).isEnabled());
		// Age (Radio button is disabled)
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='radio-disabled']")).isEnabled());
		// Biography
		Assert.assertFalse(driver.findElement(By.name("user_bio")).isEnabled());
		// Job Role 03
		Assert.assertFalse(driver.findElement(By.name("user_job3")).isEnabled());
		// Interests (Checkbox is disabled)
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='check-disbaled']")).isEnabled());
		// Slider 02 (Disabled)
		Assert.assertFalse(driver.findElement(By.id("slider-2")).isEnabled());
	}
	
	@Test
	public void TC_03_isSelected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Click
		driver.findElement(By.xpath("//input[@value='under_18']")).click();
		driver.findElement(By.xpath("//input[@id='java']")).click();
		
		// Age (Under 18)
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='under_18']")).isSelected());
		// Languagues (Java)
		Assert.assertTrue(driver.findElement(By.id("java")).isSelected());
		
		// Bỏ chọn
		driver.findElement(By.xpath("//input[@id='java']")).click();
		
		// Languagues (Java)
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='java']")).isSelected());
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
