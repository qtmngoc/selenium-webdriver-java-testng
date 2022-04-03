package webdriver;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_17_Wait_P4_Static {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new FirefoxDriver();	
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
	}

	@Test
	public void TC_01_Less() throws InterruptedException {	
		driver.findElement(By.cssSelector("div#start>button")).click();
		Thread.sleep(3000);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_02_Equal() throws InterruptedException {	
		driver.findElement(By.cssSelector("div#start>button")).click();
		Thread.sleep(5000);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_03_More() throws InterruptedException {	
		driver.findElement(By.cssSelector("div#start>button")).click();
		Thread.sleep(10000);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}