package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		System.out.println("Exercise 1:");
		
		// Email
		if (driver.findElement(By.id("mail")).isDisplayed()) {
			System.out.println("- Email textbox is displayed.");
			driver.findElement(By.id("mail")).sendKeys("element@gmail.com");
			sleepInSecond(1);
		}
		else {
			System.out.println("- Email textbox is not displayed.");
		}	
		// Age (Under 18)
		if (driver.findElement(By.id("under_18")).isDisplayed()) {
			System.out.println("- Age Under 18 radio is displayed.");
			driver.findElement(By.id("under_18")).click();
			sleepInSecond(1);
		}
		else {
			System.out.println("- Age Under 18 radio is not displayed.");
		}	
		// Education
		if (driver.findElement(By.id("edu")).isDisplayed()) {
			System.out.println("- Education textbox is displayed.");
			driver.findElement(By.id("edu")).sendKeys("Automation Testing");
			sleepInSecond(1);
		}
		else {
			System.out.println("- Education textbox is not displayed.");
		}			
		// Name: User5
		if (driver.findElement(By.xpath("//h5[text()='Name: User5']")).isDisplayed()) {
			System.out.println("- Name: User5 is displayed.");
		}
		else {
			System.out.println("- Name: User5 is not displayed.");
		}
	}

	@Test
	public void TC_02_isEnabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		System.out.println("Exercise 2:");
		
		// Number
		if (driver.findElement(By.name("number")).isEnabled()) {
			System.out.println("- Number textbox is enabled.");
		}
		else {
			System.out.println("- Number textbox is disabled.");
		}
		// Age (18 or older)
		if (driver.findElement(By.xpath("//input[@value='over_18']")).isEnabled()) {
			System.out.println("- Age 18 or older radio is enabled.");
		}
		else {
			System.out.println("- Age 18 or older radio is disabled.");
		}
		// Education
		if (driver.findElement(By.name("user_edu")).isEnabled()) {
			System.out.println("- Education textbox is enabled.");
		}
		else {
			System.out.println("- Education textbox is disabled.");
		}
		// Job Role 01
		if (driver.findElement(By.name("user_job1")).isEnabled()) {
			System.out.println("- Job Role 01 dropdown is enabled.");
		}
		else {
			System.out.println("- Job Role 01 dropdown is disabled.");
		}
		// Interests (Development)
		if (driver.findElement(By.xpath("//input[@value='interest_development']")).isEnabled()) {
			System.out.println("- Interests Development checkbox is enabled.");
		}
		else {
			System.out.println("- Interests Development checkbox is disabled.");
		}
		// Slider 01
		if (driver.findElement(By.name("slider-1")).isEnabled()) {
			System.out.println("- Slider 01 is enabled.");
		}
		else {
			System.out.println("- Slider 01 is disabled.");
		}
		
		// Password
		if (driver.findElement(By.name("disable_password")).isEnabled()) {
			System.out.println("- Password textbox is enabled.");
		}
		else {
			System.out.println("- Password textbox is disabled.");
		}
		// Age (Radio button is disabled)
		if (driver.findElement(By.xpath("//input[@value='radio-disabled']")).isEnabled()) {
			System.out.println("- Age Radio button is enabled.");
		}
		else {
			System.out.println("- Age Radio button is disabled.");
		}
		// Biography
		if (driver.findElement(By.name("user_bio")).isEnabled()) {
			System.out.println("- Biography textbox is enabled.");
		}
		else {
			System.out.println("- Biography textbox is disabled.");
		}
		// Job Role 03
		if (driver.findElement(By.name("user_job3")).isEnabled()) {
			System.out.println("- Job Role 03 dropdown is enabled.");
		}
		else {
			System.out.println("- Job Role 03 dropdown is disabled.");
		}
		// Interests (Checkbox is disabled)
		if (driver.findElement(By.xpath("//input[@value='check-disbaled']")).isEnabled()) {
			System.out.println("- Interests Checkbox is enabled.");
		}
		else {
			System.out.println("- Interests Checkbox is disabled.");
		}
		// Slider 02 (Disabled)
		if (driver.findElement(By.name("slider-2")).isEnabled()) {
			System.out.println("- Slider 02 is enabled.");
		}
		else {
			System.out.println("- Slider 02 is disabled.");
		}
	}
	
	@Test
	public void TC_03_isSelected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		System.out.println("Exercise 3:");

		// Click
		driver.findElement(By.cssSelector("input[value='under_18']")).click();
		sleepInSecond(1);
		driver.findElement(By.cssSelector("input[value='java']")).click();
		sleepInSecond(1);
		
		// Age (Under 18)
		if (driver.findElement(By.cssSelector("input[value='under_18']")).isSelected()) {
			System.out.println("- Age Under 18 radio is selected.");
		}
		else {
			System.out.println("- Age Under 18 radio is deselected.");
		}
		// Languagues (Java)
		if (driver.findElement(By.cssSelector("input[value='java']")).isSelected()) {
			System.out.println("- Languagues Java checkbox is selected.");
		}
		else {
			System.out.println("- Languagues Java checkbox is deselected.");
		}
		
		// Bỏ chọn
		driver.findElement(By.cssSelector("input[value='c#']")).click();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("input[value='c#']")).click();
		
		// Languagues (C#)
		if (driver.findElement(By.cssSelector("input[value='c#']")).isSelected()) {
			System.out.println("- Languagues C# checkbox is selected.");
		}
		else {
			System.out.println("- Languagues C# checkbox is deselected.");
		}
	}
	
	@Test
	public void TC_04_Register_Mailchimp() {
		driver.get("https://login.mailchimp.com/signup/");
		
		if (driver.findElement(By.cssSelector(".onetrust-close-btn-ui.banner-close-button")).isDisplayed()) {
			driver.findElement(By.cssSelector(".onetrust-close-btn-ui.banner-close-button")).click();
		}
		
		driver.findElement(By.id("email")).sendKeys("Exercise4@gmail.com");
		driver.findElement(By.id("new_username")).sendKeys("exercise4");
		
		WebElement password = driver.findElement(By.id("new_password"));
		WebElement signUpBtn = driver.findElement(By.id("create-account"));
		
		driver.findElement(By.xpath("//label[@for='show-password']")).click();
		
		// - One lowercase character
		password.sendKeys("auto");
		sleepInSecond(1);
		// Expected display (assertTrue)
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed' and text()='One lowercase character']")).isDisplayed());
		// Sign Up Button is disabled
		Assert.assertFalse(signUpBtn.isEnabled());
		
		// - One uppercase character 
		password.clear();
		password.sendKeys("AUTO");
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed' and text()='One uppercase character']")).isDisplayed());
		Assert.assertFalse(signUpBtn.isEnabled());
		
		// - One number
		password.clear();
		password.sendKeys("123");
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed' and text()='One number']")).isDisplayed());
		Assert.assertFalse(signUpBtn.isEnabled());
		
		// - One special characters
		password.clear();
		password.sendKeys("$%%$");
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed' and text()='One special character']")).isDisplayed());
		Assert.assertFalse(signUpBtn.isEnabled());
		
		// - 8 characters minimum
		password.clear();
		password.sendKeys("12345678");
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")).isDisplayed());
		Assert.assertFalse(signUpBtn.isEnabled());
		
		// - Valid all fields
		password.clear();
		password.sendKeys("1111aAB1@Test");
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//h4[text()=\"Your password is secure and you're all set!\"]")).isDisplayed());
		// Sign Up Button is enabled
		Assert.assertTrue(signUpBtn.isEnabled());
		
		driver.findElement(By.name("marketing_newsletter")).click();
		Assert.assertTrue(driver.findElement(By.name("marketing_newsletter")).isSelected());
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
