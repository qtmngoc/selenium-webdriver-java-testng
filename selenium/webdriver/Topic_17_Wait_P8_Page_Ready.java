package webdriver;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_P8_Page_Ready {
	WebDriver driver;
	WebElement element;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	Actions action;
	
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		
		driver = new ChromeDriver();
		action = new Actions(driver);
	}
	
	@Test
	public void TC_01_OrangeHrm_API() {
		driver.get("https://api.orangehrm.com/");
		explicitWait = new WebDriverWait(driver, 30);
		
		// Waiting for spinner invisible
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.spinner")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div#project h1")).getText(), "OrangeHRM REST API Documentation");
	}
	
	@Test
	public void TC_02_OrangeHrm_UI() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		// Login
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		
		Assert.assertTrue(isPageLoadedSuccess());
		Assert.assertTrue(driver.findElement(By.cssSelector("div#div_graph_display_emp_distribution")).isDisplayed());
		
		// Click on Pim and search
		driver.findElement(By.id("menu_pim_viewPimModule")).click();
		driver.findElement(By.cssSelector("input#empsearch_employee_name_empName")).sendKeys("Peter Mac");
		driver.findElement(By.cssSelector("input#searchBtn")).click();
		
		Assert.assertTrue(isPageLoadedSuccess());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Peter Mac']")).isDisplayed());
	}
	
	@Test
	public void TC_03_TestProject() {
		driver.get("https://blog.testproject.io/");
		
		// Close popup
		if (driver.findElement(By.cssSelector("div#mailch-bg")).isDisplayed()) {
			driver.findElement(By.cssSelector("div#close-mailch")).click();
		}
		
		// Hover mouse to search textbox 
		action.moveToElement(driver.findElement(By.cssSelector("section#search-2 input.search-field"))).perform();
		Assert.assertTrue(isPageLoadedSuccess());
		
		// Search
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("Selenium" + Keys.ENTER);
		
		Assert.assertTrue(isPageLoadedSuccess());
		
		// Verify Result
		List<WebElement> allTitlePost = driver.findElements(By.cssSelector("h3.post-title"));
		for (WebElement title : allTitlePost) {
			Assert.assertTrue(title.getText().contains("Selenium"));
		}
	}
	
	// JQuery + Javascript
	public boolean isPageLoadedSuccess() {
		
		explicitWait = new WebDriverWait(driver, 15);
		jsExecutor = (JavascriptExecutor) driver;
		
		// JQuery
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		
		// Javascript
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}