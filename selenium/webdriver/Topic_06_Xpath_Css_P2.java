package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_06_Xpath_Css_P2 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstName, lastName, fullName, emailAddress, password, nonExistedEmail;

	// Global variable
	By emailTextboxBy = By.id("email");
	By passwordTextboxBy = By.id("pass");
	By loginButtonBy = By.id("send2");
	By contactInformationBy = By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p");
	
	// Pre-condition
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		firstName = "Xpath";
		lastName = "CSS";
		fullName = firstName + " " + lastName;
		emailAddress = "xpathcss" + getRandomNumber() + "@hotmail.net";
		nonExistedEmail = "xpath-css" + getRandomNumber() + "@live.com";
		password = "9874321";

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.cssSelector(".footer a[title='My Account']")).click();
	}

	@Test
	public void Login_01_Empty_Email_Password() {
		driver.findElement(emailTextboxBy).clear();
		driver.findElement(passwordTextboxBy).clear();
		driver.findElement(loginButtonBy).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}

	@Test
	public void Login_02_Invalid_Email() {
		driver.findElement(emailTextboxBy).sendKeys("TC2@123.123");
		driver.findElement(passwordTextboxBy).sendKeys(password);
		driver.findElement(loginButtonBy).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void Login_03_Invalid_Password() {
		driver.findElement(emailTextboxBy).sendKeys(emailAddress);
		driver.findElement(passwordTextboxBy).sendKeys("123");
		driver.findElement(loginButtonBy).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void Login_04_Create_New_Account_Success() {
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		
		// Existed Email
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.cssSelector("button[title='Register']")).click();
		
		// Verify
		Assert.assertEquals(driver.findElement(By.cssSelector(".page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "Thank you for registering with Main Website Store.");
		Assert.assertEquals(driver.findElement(By.cssSelector(".hello>strong")).getText(), "Hello, " + fullName + "!");
		
		Assert.assertTrue(driver.findElement(contactInformationBy).getText().contains(fullName));
		Assert.assertTrue(driver.findElement(contactInformationBy).getText().contains(emailAddress));
		
		// Log Out
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.cssSelector("a[title='Log Out']")).click();
		
		// Verify
		Assert.assertTrue(driver.findElement(By.cssSelector(".page-title img[src$='logo.png']")).isDisplayed());
	}
	
	@Test
	public void Login_05_Incorrect_Email_Or_Password() {
		// Existed Email + incorrect Password
		driver.findElement(emailTextboxBy).sendKeys(emailAddress);
		driver.findElement(passwordTextboxBy).sendKeys("1312250");
		driver.findElement(loginButtonBy).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector(".error-msg span")).getText(), "Invalid login or password.");
		
		// Non existed Email + correct/ valid Password
		driver.findElement(emailTextboxBy).clear();
		driver.findElement(emailTextboxBy).sendKeys(nonExistedEmail);
		driver.findElement(passwordTextboxBy).clear();
		driver.findElement(passwordTextboxBy).sendKeys(password);
		driver.findElement(loginButtonBy).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector(".error-msg span")).getText(), "Invalid login or password.");
	}

	@Test
	public void Login_06_Valid_Email_Password() {
		driver.findElement(emailTextboxBy).sendKeys(emailAddress);
		driver.findElement(passwordTextboxBy).sendKeys(password);
		driver.findElement(loginButtonBy).click();
		
		// Verify
		Assert.assertEquals(driver.findElement(By.cssSelector(".page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.cssSelector(".hello>strong")).getText(), "Hello, " + fullName + "!");
				
		Assert.assertTrue(driver.findElement(contactInformationBy).getText().contains(fullName));
		Assert.assertTrue(driver.findElement(contactInformationBy).getText().contains(emailAddress));
	}

	// Post-condition
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
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

}
