package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	JavascriptExecutor jsExcutor;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		jsExcutor = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_isDisplayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		System.out.println("Exercise 1:");
		
		// Email
		elementIsDisplayed(By.id("mail"), "Email textbox");
		driver.findElement(By.id("mail")).sendKeys("element@gmail.com");
		sleepInSecond(1);
		
		// Age Under 18
		elementIsDisplayed(By.id("under_18"), "Age Under 18 radio");
		driver.findElement(By.id("under_18")).click();
		sleepInSecond(1);
		
		// Education
		elementIsDisplayed(By.id("edu"), "Education textbox");
		driver.findElement(By.id("edu")).sendKeys("Automation Testing");
		sleepInSecond(1);
		
		// Name: User5
		elementIsDisplayed(By.xpath("//h5[text()='Name: User5']"), "Name: User5");
	}

	@Test
	public void TC_02_isEnabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		System.out.println("Exercise 2:");
		
		// Number
		elementIsEnabled(By.name("number"), "Number textbox");

		// Age (18 or older)
		elementIsEnabled(By.xpath("//input[@value='over_18']"), "Age 18 or older radio");

		// Education
		elementIsEnabled(By.name("user_edu"), "Education textbox");
		
		// Job Role 01
		elementIsEnabled(By.name("user_job1"), "Job Role 01 dropdown");

		// Interests (Development)
		elementIsEnabled(By.xpath("//input[@value='interest_development']"), "Interests Development checkbox");

		// Slider 01
		elementIsEnabled(By.name("slider-1"), "Slider 01");
		
		// Password
		elementIsEnabled(By.name("disable_password"), "Password textbox");

		// Age (Radio button is disabled)
		elementIsEnabled(By.xpath("//input[@value='radio-disabled']"), "Age Radio button");

		// Biography
		elementIsEnabled(By.name("user_bio"), "Biography textbox");

		// Job Role 03
		elementIsEnabled(By.name("user_job3"), "Job Role 03 dropdown");

		// Interests (Checkbox is disabled)
		elementIsEnabled(By.xpath("//input[@value='check-disbaled']"), "Interests Checkbox");

		// Slider 02 (Disabled)
		elementIsEnabled(By.name("slider-2"), "Slider 02");
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
		elementIsSelected(By.cssSelector("input[value='under_18']"), "Age Under 18 radio");

		// Languagues (Java)
		elementIsSelected(By.cssSelector("input[value='java']"), "Languagues Java checkbox");
		
		// Bỏ chọn
		driver.findElement(By.cssSelector("input[value='c#']")).click();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("input[value='c#']")).click();
		
		// Languagues (C#)
		elementIsSelected(By.cssSelector("input[value='c#']"), "Languagues C# checkbox");
	}
	
	@Test
	public void TC_04_Register_Mailchimp() {
		driver.get("https://login.mailchimp.com/signup/");
		
		// Close Cookie banner
		List<WebElement> cookieBanner = driver.findElements(By.id("onetrust-group-container"));
		if (cookieBanner.size() > 0) {
			driver.findElement(By.cssSelector("button.banner-close-button")).click();
		}
		
		driver.findElement(By.id("email")).sendKeys("Exercise4@gmail.com");
		driver.findElement(By.id("new_username")).sendKeys("exercise4");
		
		By passwordBy = By.id("new_password");
		By signUpButtonBy = By.id("create-account");
		
		driver.findElement(By.xpath("//label[@for='show-password']")).click();
		
		// - One lowercase character
		signUpButtonDisabled("auto", By.xpath("//li[@class='lowercase-char completed' and text()='One lowercase character']"), passwordBy, signUpButtonBy);
		
		// - One uppercase character 
		signUpButtonDisabled("AUTO", By.xpath("//li[@class='uppercase-char completed' and text()='One uppercase character']"), passwordBy, signUpButtonBy);
		
		// - One number
		signUpButtonDisabled("123", By.xpath("//li[@class='number-char completed' and text()='One number']"), passwordBy, signUpButtonBy);
		
		// - One special characters
		signUpButtonDisabled("$%%$", By.xpath("//li[@class='special-char completed' and text()='One special character']"), passwordBy, signUpButtonBy);
		
		// - 8 characters minimum
		signUpButtonDisabled("12345678", By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']"), passwordBy, signUpButtonBy);
		
		// - Valid all fields
		driver.findElement(passwordBy).clear();
		sleepInSecond(1);
		driver.findElement(passwordBy).sendKeys("1111aAB1@Test");
		Assert.assertTrue(driver.findElement(By.xpath("//h4[text()=\"Your password is secure and you're all set!\"]")).isDisplayed());
		// Sign Up Button is enabled
		Assert.assertTrue(driver.findElement(signUpButtonBy).isEnabled());
		
		// Promotion checkbox
		driver.findElement(By.name("marketing_newsletter")).click();
		Assert.assertTrue(driver.findElement(By.name("marketing_newsletter")).isSelected());
		
		// Remove disabled attribute and verify error message
		driver.navigate().refresh();
		jsExcutor.executeScript("arguments[0].removeAttribute('disabled')", driver.findElement(signUpButtonBy));
		driver.findElement(signUpButtonBy).click();
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='email']/following-sibling::span[@class='invalid-error']")).getText(), "Please enter a value");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='new_username']/following-sibling::span[@class='invalid-error']")).getText(), "Please enter a value");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='new_password']/following-sibling::span[@class='invalid-error']")).getText(), "Please enter a value");
	}
	
	public boolean elementIsDisplayed(By by, String field) {
		if (driver.findElement(by).isDisplayed()) {
			System.out.println("- " + field + " is displayed.");
			return true;
		} else {
			System.out.println("- " + field + " is not displayed.");
			return false;
		}
	}
	
	public boolean elementIsSelected(By by, String field) {
		if (driver.findElement(by).isSelected()) {
			System.out.println("- " + field + " is selected.");
			return true;
		} else {
			System.out.println("- " + field + " is deselected.");
			return false;
		}
	}
	
	public boolean elementIsEnabled(By by, String field) {
		if (driver.findElement(by).isEnabled()) {
			System.out.println("- " + field + " is enabled.");
			return true;
		} else {
			System.out.println("- " + field + " is disabled.");
			return false;
		}
	}
	
	public void signUpButtonDisabled(String pw, By errorBy, By passBy, By signUpBy) {
		driver.findElement(passBy).clear();
		driver.findElement(passBy).sendKeys(pw);
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(errorBy).isDisplayed());
		Assert.assertFalse(driver.findElement(signUpBy).isEnabled());
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
