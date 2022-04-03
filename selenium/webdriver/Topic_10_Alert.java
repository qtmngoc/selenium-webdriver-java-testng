package webdriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Alert {
	WebDriver driver;
	WebDriverWait explicitWait;
	Alert alert;
	String projectPath = System.getProperty("user.dir");
	String authenChrome = projectPath + "\\autoIT\\authenAlert_chrome.exe";
	String authenFirefox = projectPath + "\\autoIT\\authenAlert_firefox.exe";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		explicitWait = new WebDriverWait(driver, 15);
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		
		// Chờ cho alert xuất hiện và switch qua
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
			
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
	}

	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();	
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		
		// Cancel an alert
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
	}
	
	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		
		String textToSendkey = "Automation Testing";
		
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();	
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		
		alert.sendKeys(textToSendkey);
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + textToSendkey);
	}
	
	@Test
	public void TC_04_Authentication_Alert_Link_1() {
		String username = "admin";
		String password = "admin";
		
		driver.get("http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations!')]")).isDisplayed());
	}
	
	@Test
	public void TC_05_Authentication_Alert_Link_2() {
		String username = "admin";
		String password = "admin";
		
		driver.get("http://the-internet.herokuapp.com/");
		String basicAuthLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		System.out.println("Old Url: " + basicAuthLink);
		
		// http://the-internet.herokuapp.com/basic_auth	
		basicAuthLink = getAuthenticateLink(basicAuthLink, username, password);
		System.out.println("New Url: " + basicAuthLink);
		driver.get(basicAuthLink);
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations!')]")).isDisplayed());
	}
	
	@Test
	public void TC_06_Authentication_Alert_AutoIT() throws IOException {
		String username = "admin";
		String password = "admin";
		driver.get("http://the-internet.herokuapp.com/");
		
		// Script chạy trước để chờ Auth Alert bật lên
		if (driver.toString().contains("Firefox")) {
			Runtime.getRuntime().exec(new String[] { authenFirefox, username, password });
		} else {
			Runtime.getRuntime().exec(new String[] { authenChrome, username, password });
		}
		
		driver.findElement(By.xpath("//a[text()='Basic Auth']")).click();
		sleepInSecond(5);		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations!')]")).isDisplayed());
	}

	public String getAuthenticateLink(String url, String username, String password) {
		String[] link = url.split("//");
		url = link[0] + "//" + username + ":" + password + "@" + link[1];
		return url;
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
