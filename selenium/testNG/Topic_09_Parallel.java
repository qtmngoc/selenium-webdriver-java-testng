package testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

// Phải run bằng file .xml
public class Topic_09_Parallel {
	WebDriver driver;
	By emailTextbox = By.cssSelector("input#email");
	By passwordTextbox = By.cssSelector("input#pass");
	By loginButton = By.cssSelector("button[name='login']");
	String projectPath = System.getProperty("user.dir");

	@Parameters({"browser", "url"})
	@BeforeClass
	public void beforeClass(String browserName, String url) {
		switch (browserName) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;

		default:
			new RuntimeException("Please input the browser name");
			break;
		}
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(url);
	}
	
	@Test
	public void TC_01_Login_To_System() {
		driver.findElement(emailTextbox).sendKeys("selenium_11_01@gmail.com");
		driver.findElement(passwordTextbox).sendKeys("111111");
		driver.findElement(loginButton).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Find your account and log in.']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}