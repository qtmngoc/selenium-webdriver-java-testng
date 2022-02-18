package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Topic_03_Run_On_Multiple_Browsers {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@Test
	public void TC_01_Firefox() {
		// Executable File: geckodriver/ chromedriver/ edgedriver/ IEDriver/...
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		
		// Class: FirefoxDriver/ ChromeDriver/ EgdeDriver/ SafariDriver/...
		driver = new FirefoxDriver();
		
		driver.get("https://www.facebook.com/");
		driver.quit();
	}

	@Test
	public void TC_02_Chrome() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://www.facebook.com/");
		driver.quit();
	}
	
	@Test
	public void TC_03_Edge() {
		System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
		driver = new EdgeDriver();
		
		driver.get("https://www.facebook.com/");
		driver.quit();
	}
	
}
