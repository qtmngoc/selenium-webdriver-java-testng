package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Actions_P1 {
	WebDriver driver;
	Actions action;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name").toLowerCase();

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		action = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Hover_jQuery() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		WebElement yourAgeTextbox = driver.findElement(By.id("age"));
		
		// Hover chuột vào textbox
		action.moveToElement(yourAgeTextbox).perform();
		sleepInSecond(1);
		
		Assert.assertEquals(driver.findElement(By.className("ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}

	@Test
	public void TC_02_Hover_Myntra() {
		driver.get("http://www.myntra.com/");
		
		action.moveToElement(driver.findElement(By.cssSelector("a[data-group='kids']"))).perform();
		sleepInSecond(1);
		
		action.click(driver.findElement(By.xpath("//a[text()='Home & Bath']"))).perform();

		Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Kids Home Bath']")).isDisplayed());	
	}
	
	@Test
	public void TC_03_Click_And_Hold_Multiple() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		action.clickAndHold(allNumbers.get(0)).moveToElement(allNumbers.get(3)).release().perform();
		sleepInSecond(1);
		
		List<WebElement> allNumberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumberSelected.size(), 4);	
	}
	
	@Test
	public void TC_04_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		Keys controlKey;
		if (osName.contains("win") || osName.contains("nux")) {
			controlKey = Keys.CONTROL;
		} else {
			controlKey = Keys.COMMAND;
		}
		
		action.keyDown(controlKey).perform();
		action.click(allNumbers.get(0)).click(allNumbers.get(2)).click(allNumbers.get(5)).click(allNumbers.get(10)).click(allNumbers.get(7)).perform();
		action.keyUp(controlKey).perform();
		sleepInSecond(1);
		
		List<WebElement> allNumberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumberSelected.size(), 5);	
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