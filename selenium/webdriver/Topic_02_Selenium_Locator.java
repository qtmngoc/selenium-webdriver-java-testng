package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_Locator {
	// Declare a variable representing Selenium WebDriver
	// Global variable
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");

		// Browser initialization
		driver = new FirefoxDriver();

		// Wait time is set to find an element
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Open test page
		driver.get("http://live.techpanda.org/index.php/customer/account/create/");
	}

	// Selenium Locator (By class)
	@Test
	public void TC_01_ID() {
		driver.findElement(By.id("firstname")).sendKeys("Quach");
		sleepInSecond(1);
	}

	@Test
	public void TC_02_Name() {
		driver.findElement(By.name("lastname")).sendKeys("Ngoc");
		sleepInSecond(1);
	}

	@Test
	public void TC_03_Class() {
		driver.findElement(By.className("customer-name-middlename")).isDisplayed();
		driver.findElement(By.className("name-middlename")).isDisplayed();
	}

	@Test
	public void TC_04_Tagname() {
		int inputNumber = driver.findElements(By.tagName("input")).size();
		System.out.println(inputNumber);
	}

	@Test
	public void TC_05_LinkText() {
		driver.findElement(By.linkText("SEARCH TERMS")).click();
		sleepInSecond(1);
	}

	@Test
	public void TC_06_PartialLinkText() {
		driver.findElement(By.partialLinkText("ADVANCED")).click();
		sleepInSecond(1);
	}

	@Test
	public void TC_07_Css() {
		driver.findElement(By.cssSelector("input[id='name']")).sendKeys("iPhone");
		sleepInSecond(1);

		driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Samsung Note 20");
		sleepInSecond(1);

		driver.findElement(By.cssSelector("#name")).clear();
		driver.findElement(By.cssSelector("#name")).sendKeys("Nokia 110i");
		sleepInSecond(1);
	}

	@Test
	public void TC_08_Xpath() {
		driver.findElement(By.xpath("//input[@id='description']")).sendKeys("iPhone 13 ");
		sleepInSecond(1);

		driver.findElement(By.xpath("//input[@name='description']")).sendKeys("Korea ");
		sleepInSecond(1);

		driver.findElement(By.xpath("//label[text()='Description']/following-sibling::div/input")).clear();
		driver.findElement(By.xpath("//label[text()='Description']/following-sibling::div/input")).sendKeys("Vietnam");
		sleepInSecond(1);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
